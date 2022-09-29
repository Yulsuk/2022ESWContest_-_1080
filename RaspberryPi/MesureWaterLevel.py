import math
import time

import RPi.GPIO as GPIO
from time import sleep,time

GPIO.setmode(GPIO.BCM) 
GPIO.setwarnings(False)

GPIO_TRIGGER = 17
GPIO_ECHO = 18

GPIO.setup(GPIO_TRIGGER, GPIO.OUT)
GPIO.setup(GPIO_ECHO, GPIO.IN)

GPIO.output(GPIO_TRIGGER, False) 


class MesureWaterLevel:
    def __init__(self, waterLevel, installedHeight):
        self.__waterLevel = waterLevel
        self.__installedHeight = installedHeight
        
        
    def setWaterLevel(self):
        GPIO.output(GPIO_TRIGGER, True)
        sleep(1)
        GPIO.output(GPIO_TRIGGER, False)
        StartTime = time()
        StopTime = time()

        while GPIO.input(GPIO_ECHO) == 0:
            StartTime = time()
        while GPIO.input(GPIO_ECHO) == 1:
            StopTime = time()
            
        TimeElapsed = StopTime - StartTime
        self.__waterLevel = self.__installedHeight - (TimeElapsed * 34300)/2
    
    def getWaterLevel(self):
        return round(self.__waterLevel,2)
    
    class DetectionWaterLevelRise:
        def __init__(self, firstWaterLevel, detectionWaterLevelRise):
            self.__firstWaterLevel = firstWaterLevel
            self.__detectionWaterLevelRise = detectionWaterLevelRise
                
        def setDetectionWaterLevelRise(self, nowWaterLevel):
            self.__detectionWaterLevelRise = nowWaterLevel - self.__firstWaterLevel
                
        def getDetectionWaterLevelRise(self):
            return round(self.__detectionWaterLevelRise, 2)
        
        class DetectionFloodRisk:
            def __init__(self, waterLevel, detectionFloodRisk, warningWaterLevel):
                self.__waterLevel = waterLevel
                self.__detectionFloodRisk = detectionFloodRisk
                self.__warningWaterLevel = warningWaterLevel
            
            def setFloodRisk(self, waterLevel):
                self.__waterLevel = waterLevel
                
                if self.__waterLevel >= self.__warningWaterLevel:
                    self.__detectionFloodRisk = True
                else:
                    self.__detectionFloodRisk = False
            
            def getFloodRisk(self):
                return self.__detectionFloodRisk
        
if __name__ == "__main__":
    float_waterLevel = 0.0
    height = 15.0
    float_waterLevel_rise = 0.0
    boolean_flood_risk = False
    float_warning_waterLevel = 3.0

    obj1 = MesureWaterLevel(float_waterLevel,height)
    
    obj1.setWaterLevel()
    float_waterLevel = obj1.getWaterLevel()
    rise = obj1.DetectionWaterLevelRise(float_waterLevel, float_waterLevel_rise)
    
    risk = rise.DetectionFloodRisk(float_waterLevel_rise, boolean_flood_risk, float_warning_waterLevel)
    
    while True:
        obj1.setWaterLevel()
        float_waterLevel = obj1.getWaterLevel()
        
        rise.setDetectionWaterLevelRise(float_waterLevel)
        float_waterLevel_rise = rise.getDetectionWaterLevelRise()
        
        risk.setFloodRisk(float_waterLevel_rise)
        boolean_flood_risk = risk.getFloodRisk()
        
        print(float_waterLevel)
        print(float_waterLevel_rise)
        print(boolean_flood_risk)
        sleep(1)