import os
import glob
import time

os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')

base_dir = '/sys/bus/w1/devices/'
device_folder = glob.glob(base_dir + '28*')[0]
device_file = device_folder + '/w1_slave'

class MesureWaterTemperature:
    def __init__(self,waterTemperatrue):
        self.__waterTemperatrue = waterTemperatrue
    
    def setWaterTemperature(self):
        f = open(device_file, 'r')
        lines = f.readlines()
        f.close()
        
        while lines[0].strip()[-3:] != 'YES':
            time.sleep(0.5)
            lines = __read_temp_ras()
            
        equals_pos = lines[1].find('t=')
        if equals_pos != -1:
            temp_string = lines[1][equals_pos+2:]
            self.__waterTemperatrue = float(temp_string) / 1000.0
    
    def getWaterTemperature(self):
        return self.__waterTemperatrue
        
    
if __name__ == "__main__":
    float_temp = 0;
    aaa = MesureWaterTemperature(float_temp)
    
    while True:
        aaa.setWaterTemperature()
        float_temp = aaa.getWaterTemperature()
        print(float_temp)
        time.sleep(1)
    
    

