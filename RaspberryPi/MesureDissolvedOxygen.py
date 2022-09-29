import time
from ClassMethVar import bus
from ClassMethVar import device_list
from ClassMethVar import do_address
from ClassMethVar import ASCIIStrListToFloatVar

class MesureDissolvedOxygen:
    
    def __init__(self,DissolvedOxygen):
        self.__DissolvedOxygen = DissolvedOxygen

    def setDissolvedOxygen(self):
        device_list[0].write("R")
        time.sleep(1)
        self.__DissolvedOxygen = ASCIIStrListToFloatVar(bus.read_i2c_block_data(do_address,0,6))
    
    def getDissolvedOxygen(self):
        return self.__DissolvedOxygen
    
if __name__ == "__main__":
    obj = MesureDissolvedOxygen(0)
    while True:
        obj.setDissolvedOxygen()
        print(obj.getDissolvedOxygen())