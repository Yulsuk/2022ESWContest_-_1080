import math

class JudgementWaterQualityRating:
    
    def __init__(self, judgementWaterQualityRating, ph, dissolvedOxygen):
        self.__judgementWaterQualityRating = judgementWaterQualityRating
        self.__ph = ph
        self.__dissolvedOxygen = dissolvedOxygen
    
    def setJudgementWaterQualityRating(self, ph, do):
        self.__ph = ph
        self.__dissolvedOxygen = do
        if   (self.__ph >= 6.5 and self.__ph <= 8.5) and self.__dissolvedOxygen >= 7.5 :
            self.__judgementWaterQualityRating = 1.0
        elif (self.__ph >= 6.5 and self.__ph <= 8.5) and self.__dissolvedOxygen >= 5.0 :
            self.__judgementWaterQualityRating = 2.0
        elif (self.__ph >= 6.5 and self.__ph <= 8.5) and self.__dissolvedOxygen >= 5.0 :
            self.__judgementWaterQualityRating = 3.0
        elif (self.__ph >= 6.5 and self.__ph <= 8.5) and self.__dissolvedOxygen >= 5.0 :
            self.__judgementWaterQualityRating = 4.0
        elif (self.__ph >= 6.0 and self.__ph <= 8.5) and self.__dissolvedOxygen >= 2.0 :
            self.__judgementWaterQualityRating = 5.0
        elif (self.__ph >= 6.0 and self.__ph <= 8.5) and self.__dissolvedOxygen >= 2.0 :
            self.__judgementWaterQualityRating = 6.0
        else :
            self.__judgementWaterQualityRating = 7.0
    
    def getJudgementWaterQualityRating(self):
        return (int)(self.__judgementWaterQualityRating)