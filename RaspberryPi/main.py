import time
import pymysql

from MesureDissolvedOxygen import MesureDissolvedOxygen
from MesurePh import MesurePh
from MesureWaterLevel import MesureWaterLevel #in class FloodRisk, you should change standard
from MesureWaterTemperature import MesureWaterTemperature

from GreenTideWarning import GreenTideWarning
from JudgementWaterQualityRating import JudgementWaterQualityRating
from WaterResourceUtilizationField import WaterResourceUtilizationField #in class, you should check UtilizationField

from ClassMethVar import installedHeight
from ClassMethVar import warningWaterLevel

import random


def InsertData(waterLevel,ph,do,waterTemperature,detectionWaterLevelRise,waterQualityRating,greenTide,floodRisk,waterUtilizationField):
    conn = pymysql.connect(host="220.69.240.24", user="seras", password="selab", db="iwlmp", charset="utf8")
    cursor = conn.cursor()
    
    sql="INSERT INTO fromras(waterLevel,ph,dissolvedOxygen,waterTemperature,detectionWaterLevelRise,waterQualityRating,greenTide,floodRisk,waterUtilizationField) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    cursor.execute(sql,(waterLevel,ph,do,waterTemperature,detectionWaterLevelRise,waterQualityRating,greenTide,floodRisk,waterUtilizationField))
    
    conn.commit()
    conn.close()


if __name__ == "__main__":    
    #Variable to send DB
    float_doData = 0                    
    float_phData = 0
    float_waterLevel = 0
    float_waterTemperature = 0
    
    int_judgementWaterQualityRating = 0
    float_waterUtilizationField = 0
    
    float_detectionWaterLevelRise = 0
    boolean_greenTide = False
    boolean_floodRisk = False
    
    
    #Declare and Init Object
    dissolvedOxygen = MesureDissolvedOxygen(float_doData)
    ph = MesurePh(float_phData)
    waterLevel = MesureWaterLevel(float_waterLevel, installedHeight)
    waterTemperature = MesureWaterTemperature(float_waterTemperature)
    
    waterQualityRating = JudgementWaterQualityRating(int_judgementWaterQualityRating,float_phData,float_doData)
    waterUtilizationField = WaterResourceUtilizationField(float_waterUtilizationField, int_judgementWaterQualityRating)
    
    detectionFloodRisk = waterLevel.DetectionWaterLevelRise.DetectionFloodRisk(float_waterLevel, boolean_floodRisk, warningWaterLevel)
    detectionGreenTide = GreenTideWarning(float_waterTemperature, boolean_greenTide)
    
    #For declairtion detectionWaterLevelRise. It needs first waterLevel
    waterLevel.setWaterLevel()
    float_waterLevel = waterLevel.getWaterLevel()
    detectionWaterLevelRise = waterLevel.DetectionWaterLevelRise(float_waterLevel, float_detectionWaterLevelRise)
    
    #get(),set(),InsertData() in While
    while True:
        #DO set get
        #dissolvedOxygen.setDissolvedOxygen()
        #float_doData = dissolvedOxygen.getDissolvedOxygen()
        #float_doData = 7.5
        float_doData = round(random.uniform(7,7.5),3)
        
        #Ph set get
        ph.setPh()
        float_phData = ph.getPh()
        
        #WaterLevel set get
        waterLevel.setWaterLevel()
        float_waterLevel = waterLevel.getWaterLevel()
        
        #WaterTemperatrue set get
        waterTemperature.setWaterTemperature()
        float_waterTemperature = waterTemperature.getWaterTemperature()
                
        #WaterLevelRise set get
        detectionWaterLevelRise.setDetectionWaterLevelRise(float_waterLevel)
        float_detectionWaterLevelRise = detectionWaterLevelRise.getDetectionWaterLevelRise()
        
        #FloodRisk set get
        detectionFloodRisk.setFloodRisk(float_waterLevel)
        boolean_floodRisk = detectionFloodRisk.getFloodRisk()
        
        #GreenTideWarning set get
        detectionGreenTide.setDetectGreenTide(float_waterTemperature)
        boolean_greenTide = detectionGreenTide.getDetectGreenTide()
        
        #WaterQuality set get
        waterQualityRating.setJudgementWaterQualityRating(float_phData, float_doData)
        int_judgementWaterQualityRating = waterQualityRating.getJudgementWaterQualityRating()
        
        #WaterUtilization set get
        waterUtilizationField.setUtilizationField(int_judgementWaterQualityRating)
        float_waterUtilizationField = waterUtilizationField.getUtilizationField()
        
        print("----------Sensor Data----------")
        print("DO :", float_doData, "Ph :", float_phData)
        print("WaterLevel :", float_waterLevel, "WaterTemperture :", float_waterTemperature)
        print("-------------------------------")
        print("DetectionWaterLevelRise :", float_detectionWaterLevelRise)
        print("FloodRisk :", boolean_floodRisk)
        print("GreenTideWarning :", boolean_greenTide)
        print("WaterQualityRating :", int_judgementWaterQualityRating)
        print("WaterUtilizationField :", float_waterUtilizationField)
        
        InsertData(float_waterLevel,float_phData,float_doData,float_waterTemperature,float_detectionWaterLevelRise,int_judgementWaterQualityRating,boolean_greenTide,boolean_floodRisk,float_waterUtilizationField)

        time.sleep(5)