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

import cv2
import numpy as np
from networktables import NetworkTables
from utils.grip import GripRetroreflectivePipeline
from cscore import CameraServer

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

    print('Initializing NetworkTables')
    NetworkTables.initialize(server='10.5.55.2')
    cs = CameraServer.getInstance()
    cs.enableLogging()
    camera = cs.startAutomaticCapture()
    camera.setResolution(640, 480)
    # camera.setExposureManual(15)
    cvSink = cs.getVideo()
    outputStream = cs.putVideo("Port Targeting", 320, 240)
    img = np.zeros(shape=(480, 640, 3), dtype=np.uint8)

    print('Creating video capture')

    print('Creating pipeline')
    pipeline = GripRetroreflectivePipeline()

    print('Running pipeline')
    while True:
        time, img = cvSink.grabFrame(img)
        if time == 0:
            outputStream.notifyError(cvSink.getError());
            continue

        pipeline.process(img)
        processed_frame = extra_processing(pipeline, img)
        outputStream.putFrame(processed_frame)


    print('Capture closed')


if __name__ == '__main__':
    main()
