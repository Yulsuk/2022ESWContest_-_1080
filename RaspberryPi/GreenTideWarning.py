class GreenTideWarning:
    def __init__ (self, waterTemperature, greenTideWarning):
        self.__waterTemperature = waterTemperature
        self.__greenTideWarning = greenTideWarning
        
    def setDetectGreenTide(self, waterTemperature):
        self.__waterTemperature = waterTemperature
        
        if self.__waterTemperature >= 20:
            self.__greenTideWarning = True
        else:
            self.__greenTideWarning = False
        
    def getDetectGreenTide(self):
        return self.__greenTideWarning