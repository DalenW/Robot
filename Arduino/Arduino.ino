#include <Servo.h>

Servo s[12];

char buffer[24];

void setup() {
  for(int i = 0; i < 12; i++){
    s[i].attach(i+1);
    s[i].write(0);
  }

  Serial.begin(9600);
}

void loop() {
  while(Serial.available() == 24){
    for(int i = 0; i < 24; i++){
      buffer[i] = Serial.read();
    }
    Serial.write(buffer);

    for(int i = 0; i < 12; i++){
      int b = i * 2;
      s[i].write((hex2dec(buffer[b]) * 16 + hex2dec(buffer[b+1])));
      //s[0].write(180);
    }
  }
}

byte hex2dec(byte c) { //c is the character that was buffered
  if (c >= '0' && c <= '9') {
    return c - '0';
  }       
  else if (c >= 'A' && c <= 'F') {
    return c - 'A' + 10;
  }       
  else if (c >= 'a' && c <= 'f') {
    return c - 32 - 'A' + 10;
  }       
}
