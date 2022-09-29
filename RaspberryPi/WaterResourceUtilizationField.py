class WaterResourceUtilizationField:
    def __init__(self, utilizationField, waterQulityRating):
        self.__utilizationField = utilizationField
        self.__waterQulityRating = waterQulityRating
    
    def setUtilizationField(self, waterQulityRating):
        self.__waterQulityRating = waterQulityRating
        
        if self.__waterQulityRating >= 1 and self.__waterQulityRating <=2:
            self.__utilizationField = 1.0#living water
        elif self.__waterQulityRating == 3:
            self.__utilizationField = 2.0#living or swiming
        elif self.__waterQulityRating >= 4 and self.__waterQulityRating <=6:
            self.__utilizationField = 3.0#industry water, agriculture water
        else:
            self.__utilizationField = 4.0#
    
    def getUtilizationField(self):
        return self.__utilizationField