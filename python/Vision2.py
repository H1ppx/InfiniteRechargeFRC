# ==============================================================================
#     This file is part of COVID.
#
#     Copyright (c) 2020 William Chu
#
#     COVID is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     (at your option) any later version.
#
#     COVID is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
#
#     You should have received a copy of the GNU General Public License
#     along with COVID.  If not, see <https://www.gnu.org/licenses/>.
# ==============================================================================

import os
import cv2
import numpy as np
import tensorflow as tf
from networktables import NetworkTables
from cscore import CameraServer

from utils.grip import GripRetroreflectivePipeline
from utils import label_map_util
from utils import visualization_utils as vis_util


def extra_processing(pipeline, frame):
    detectedX = []
    detectedY = []
    table = NetworkTables.getTable('Vision/Port')
    print(pipeline.filter_contours_output.__len__())
    for contour in pipeline.filter_contours_output:
        rect = cv2.minAreaRect(contour)
        point, dimensions, angle = rect
        boxPoints = cv2.boxPoints(rect)

        boxPoints = np.int0(boxPoints)
        x, y = np.sum(boxPoints, axis = 0)/4

        cv2.drawContours(frame,[boxPoints],0,(0,0,255),2)
        cv2.circle(frame, (int(x), int(y)), 4, (0, 0, 255))

        detectedX.append(x)
        detectedY.append(y)

        if len(detectedX)>0:
            table.putBoolean('Tgt Detected', True)
            table.putNumber('x', detectedX[0])
            table.putNumber('y', detectedY[0])
        else:
            table.putBoolean('Tgt Detected', False)

    return frame


def main():

    CWD_PATH = os.getcwd()
    PATH_TO_CKPT = os.path.join(CWD_PATH, 'utils/frozen_inference_graph.pb')
    PATH_TO_LABELS = os.path.join(CWD_PATH, 'utils/labelmap.pbtxt')

    print(CWD_PATH)
    label_map = label_map_util.load_labelmap(PATH_TO_LABELS)
    categories = label_map_util.convert_label_map_to_categories(label_map, max_num_classes=1, use_display_name=True)
    category_index = label_map_util.create_category_index(categories)

    detection_graph = tf.Graph()
    with detection_graph.as_default():
        od_graph_def = tf.GraphDef()
        with tf.gfile.GFile(PATH_TO_CKPT, 'rb') as fid:
            serialized_graph = fid.read()
            od_graph_def.ParseFromString(serialized_graph)
            tf.import_graph_def(od_graph_def, name='')

        sess = tf.Session(graph=detection_graph)

    image_tensor = detection_graph.get_tensor_by_name('image_tensor:0')
    detection_boxes = detection_graph.get_tensor_by_name('detection_boxes:0')
    detection_scores = detection_graph.get_tensor_by_name('detection_scores:0')
    detection_classes = detection_graph.get_tensor_by_name('detection_classes:0')
    num_detections = detection_graph.get_tensor_by_name('num_detections:0')

    print('Initializing NetworkTables')
    NetworkTables.initialize(server='10.5.55.2')
    cs = CameraServer.getInstance()
    cs.enableLogging()

    frontCam = cs.startAutomaticCapture(name = "front", dev=0)
    backCam = cs.startAutomaticCapture(name = "back", dev=2)


    frontCam.setResolution(640, 480)
    #frontCam.setExposureManual(15)

    backCam.setResolution(640, 480)
    # frontCam.setExposureManual(15)

    cvSinkFront = cs.getVideo(name="front")
    cvSinkBack = cs.getVideo(name="back")

    outputStream = cs.putVideo("Port Targeting", 320, 240)
    imgFront = np.zeros(shape=(480, 640, 3), dtype=np.uint8)
    imgBack = np.zeros(shape=(480, 640, 3), dtype=np.uint8)

    print('Creating video capture')

    print('Creating pipeline')
    pipeline = GripRetroreflectivePipeline()

    print('Running pipeline')
    while True:
        timeFront, imgFront = cvSinkFront.grabFrame(imgFront)

        timeBack, imgBack = cvSinkBack.grabFrame(imgBack)

        if timeFront == 0 or timeBack == 0:
            outputStream.notifyError(cvSinkFront.getError());
            continue

        frame_expanded = np.expand_dims(imgBack, axis=0)
        (boxes, scores, classes, num) = sess.run(
            [detection_boxes, detection_scores, detection_classes, num_detections],
            feed_dict={image_tensor: frame_expanded})

        pipeline.process(imgFront)
        processed_frame = extra_processing(pipeline, imgFront)
        outputStream.putFrame(processed_frame)

        vis_util.visualize_boxes_and_labels_on_image_array(
            imgBack,
            np.squeeze(boxes),
            np.squeeze(classes).astype(np.int32),
            np.squeeze(scores),
            category_index,
            use_normalized_coordinates=True,
            line_thickness=8,
            min_score_thresh=0.60)

        cv2.imshow('Port Side', imgFront)
        cv2.imshow('Intake Test', imgBack)

        if cv2.waitKey(1) == ord('q'):
            break

    print('Capture closed')


if __name__ == '__main__':
    main()
