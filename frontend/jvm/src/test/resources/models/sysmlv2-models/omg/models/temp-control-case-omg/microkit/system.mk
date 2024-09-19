ifeq ($(strip $(MICROKIT_SDK)),)
$(error MICROKIT_SDK must be specified)
endif

BUILD_DIR ?= build
# By default we make a debug build so that the client debug prints can be seen.
MICROKIT_CONFIG ?= debug

QEMU := qemu-system-aarch64

CC := clang
LD := ld.lld
AR := llvm-ar
RANLIB := llvm-ranlib

MICROKIT_TOOL ?= $(MICROKIT_SDK)/bin/microkit

CFLAGS := -mcpu=$(CPU) \
	-mstrict-align \
	-nostdlib \
	-ffreestanding \
	-g3 \
	-O3 \
	-Wall -Wno-unused-function -Werror -Wno-unused-command-line-argument \
	-target aarch64-none-elf \
	-I$(BOARD_DIR)/include
LDFLAGS := -L$(BOARD_DIR)/lib
LIBS := --start-group -lmicrokit -Tmicrokit.ld --end-group


PRINTF_OBJS := printf.o util.o
TSP_TEMPSENSOR_OBJS := $(PRINTF_OBJS) tsp_tempSensor.o
TCP_TEMPCONTROL_OBJS := $(PRINTF_OBJS) tcp_tempControl.o
FP_FAN_OBJS := $(PRINTF_OBJS) fp_fan.o
OIP_OPERATORINTERFACE_OBJS := $(PRINTF_OBJS) oip_operatorInterface.o
PACER_OBJS := $(PRINTF_OBJS) pacer.o
END_OF_FRAME_COMPONENT_OBJS := $(PRINTF_OBJS) end_of_frame_component.o

SYSTEM_FILE := ${TOP}/microkit.system

IMAGES := tsp_tempSensor.elf tcp_tempControl.elf fp_fan.elf oip_operatorInterface.elf pacer.elf end_of_frame_component.elf
IMAGE_FILE_DATAPORT = microkit.img
IMAGE_FILE = loader.img
REPORT_FILE = /report.txt

all: $(IMAGE_FILE)
	CHECK_FLAGS_BOARD_MD5:=.board_cflags-$(shell echo -- ${CFLAGS} ${BOARD} ${MICROKIT_CONFIG}| shasum | sed 's/ *-//')

${CHECK_FLAGS_BOARD_MD5}:
	-rm -f .board_cflags-*
	touch $@

%.o: ${TOP}/%.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

printf.o: ${TOP}/include/printf.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

util.o: ${TOP}/include/util.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

tsp_tempSensor_user.o: ${TOP}/components/tsp_tempSensor/src/tsp_tempSensor_user.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@
tsp_tempSensor.o: ${TOP}/components/tsp_tempSensor/src/tsp_tempSensor.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

tcp_tempControl_user.o: ${TOP}/components/tcp_tempControl/src/tcp_tempControl_user.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@
tcp_tempControl.o: ${TOP}/components/tcp_tempControl/src/tcp_tempControl.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

fp_fan_user.o: ${TOP}/components/fp_fan/src/fp_fan_user.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@
fp_fan.o: ${TOP}/components/fp_fan/src/fp_fan.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

oip_operatorInterface_user.o: ${TOP}/components/oip_operatorInterface/src/oip_operatorInterface_user.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@
oip_operatorInterface.o: ${TOP}/components/oip_operatorInterface/src/oip_operatorInterface.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

pacer.o: ${TOP}/components/pacer/src/pacer.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

end_of_frame_component.o: ${TOP}/components/end_of_frame_component/src/end_of_frame_component.c Makefile
	$(CC) -c $(CFLAGS) $< -o $@

tsp_tempSensor.elf: $(PRINTF_OBJS) tsp_tempSensor_user.o tsp_tempSensor.o
	$(LD) $(LDFLAGS) $^ $(LIBS) -o $@

tcp_tempControl.elf: $(PRINTF_OBJS) tcp_tempControl_user.o tcp_tempControl.o
	$(LD) $(LDFLAGS) $^ $(LIBS) -o $@

fp_fan.elf: $(PRINTF_OBJS) fp_fan_user.o fp_fan.o
	$(LD) $(LDFLAGS) $^ $(LIBS) -o $@

oip_operatorInterface.elf: $(PRINTF_OBJS) oip_operatorInterface_user.o oip_operatorInterface.o
	$(LD) $(LDFLAGS) $^ $(LIBS) -o $@

pacer.elf: $(PRINTF_OBJS)  pacer.o
	$(LD) $(LDFLAGS) $^ $(LIBS) -o $@

end_of_frame_component.elf: $(PRINTF_OBJS)  end_of_frame_component.o
	$(LD) $(LDFLAGS) $^ $(LIBS) -o $@

$(IMAGE_FILE): $(IMAGES) $(SYSTEM_FILE)
	$(MICROKIT_TOOL) $(SYSTEM_FILE) --search-path $(BUILD_DIR) --board $(MICROKIT_BOARD) --config $(MICROKIT_CONFIG) -o $(IMAGE_FILE) -r $(REPORT_FILE)


qemu: $(IMAGE_FILE)
	$(QEMU) -machine virt,virtualization=on \
			-cpu cortex-a53 \
			-serial mon:stdio \
			-device loader,file=$(IMAGE_FILE),addr=0x70000000,cpu-num=0 \
			-m size=2G \
			-nographic

clean::
	rm -f 

clobber:: clean
	rm -f tsp_tempSensor.elf tcp_tempControl.elf fp_fan.elf oip_operatorInterface.elf pacer.elf end_of_frame_component.elf ${IMAGE_FILE} ${REPORT_FILE}
