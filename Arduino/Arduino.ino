#include <Servo.h>

//(hex2dec(buffer[b]) * 16) + hex2dec(buffer[b+1])

Servo s[12];

byte buffer[25];

void setup() {
  for(int i = 0; i < 12; i++){
    s[i].attach(i+2);
    //s[i].write(0);
  }

  Serial.begin(9600);
}

void loop() {
  while(true){
    while(Serial.available() == 25){
      
      for(int i = 0; i < 25; i++){
        buffer[i] = Serial.read();
      }
      s[0].write(45);
      if(buffer[0] == 'T'){
        //Serial.println(buffer);
        //Serial.println(180);
        Serial.println("Here");
        s[0].write(180);
        /*
        if((hex2dec(buffer[1]) * 16) + hex2dec(buffer[2]) == 180){
          s[0].write(180);
        }
        */
        
        for(int i = 0; i < 12; i++){
          int b = i * 2;
          b = b+1;
          Serial.println((hex2dec(buffer[b]) * 16) + hex2dec(buffer[b+1]));
          //s[i].write((hex2dec(buffer[b]) * 16) + hex2dec(buffer[b+1]));
        }
        
        
        loop();
      } else {
        loop();
      }
    }
  }
}

int hex2dec(byte c) { //c is the character that was buffered
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
