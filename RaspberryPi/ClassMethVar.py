import smbus
import i2c

def ASCIIStrListToFloatVar(arr):  #ASCII int list to float var
        str_list = []
        for i in range( 1, len(arr) ):
            if arr[i] == 0:
                break
            str_list.append( chr(arr[i]) )
        float_var = float(''.join(str_list))
        
        return float_var

bus = smbus.SMBus(1)            #Enable SMBus
device_list = i2c.get_devices() #Use 'get_devices' fucntion in i2c.py

do_address = 0x61
ph_address = 0x63

installedHeight = 16.0
warningWaterLevel = 10.0