FROM ubuntu:latest
LABEL authors="KDP"

ENTRYPOINT ["top", "-b"]