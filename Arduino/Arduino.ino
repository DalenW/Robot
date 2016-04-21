#include <Servo.h>

Servo s[12];
int v[12];
char buffer[25];

void setup() {
  for(int i = 0; i < 12; i++){
    s[i].attach(i+2);
  }
  Serial.begin(115200);
}

void loop() {
  while(Serial.available() == 25){
    Serial.readBytes(buffer, Serial.available());
    if(buffer[0] == 'T'){
      for(int i = 0; i < 12; i++){
        int b = i * 2;
        b = b+1;
        v[i] = (hex2dec(buffer[b]) * 16) + hex2dec(buffer[b+1]);
        s[i].write(v[i]);
      }
    }
    writeSensors();
  }
}

void writeSensors(){
  String msg = "`";

  //msg = msg + sensLine("temperature", (getVoltage(tempSens) - 0.5) * 100.0);  
  msg += sensLine("temperature", 3.8644567);
  msg += sensLine("depth", 5.00);
  Serial.print(msg + "*");
}

String sensLine(String n, double v){
  return "$" + n + "/" + v + "&";
}

float getVoltage(int p){
  return(analogRead(p) * 0.004882814);
}

int hex2dec(char c) { //c is the character that was buffered
  if(c == '0'){
    return 0;
  } else if(c == '1'){
    return 1;    
  } else if(c == '2'){
    return 2;    
  } else if(c == '3'){
    return 3;    
  } else if(c == '4'){
    return 4;    
  } else if(c == '5'){
    return 5;    
  } else if(c == '6'){
    return 6;    
  } else if(c == '7'){
    return 7;    
  } else if(c == '8'){
    return 8;    
  } else if(c == '9'){
    return 9;    
  } else if(c == 'A'){
    return 10;    
  } else if(c == 'B'){
    return 11;    
  } else if(c == 'C'){
    return 12;    
  } else if(c == 'D'){
    return 13;    
  } else if(c == 'E'){
    return 14;    
  } else if(c == 'F'){
    return 15;    
  } else {
    return 0;
  }
}
