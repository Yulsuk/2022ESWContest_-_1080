import time
from ClassMethVar import bus
from ClassMethVar import device_list
from ClassMethVar import ph_address
from ClassMethVar import ASCIIStrListToFloatVar

class MesurePh:
    
    def __init__(self, Ph):
        self.__Ph = Ph
    
    def setPh(self):
        device_list[0].write("R")
        time.sleep(1)
        self.__Ph = ASCIIStrListToFloatVar(bus.read_i2c_block_data(ph_address,0,6))
    
    def getPh(self):
        return self.__Ph

if __name__ == "__main__":
    obj = MesurePh(0)
    while True:
        obj.setPh()
        print(obj.getPh())
        #device_list[1].write("cal,mid,7")
        #device_list[1].write("cal,low,4")
        #device_list[1].write("cal,high,10")
        time.sleep(1)