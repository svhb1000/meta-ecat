#
# SPDX-License-Identifier: MIT
# Copyright (c) 2020-2021, 2023 Tano Systems LLC. All rights reserved.
#

#Based on the bb from Tano Systems (https://tano-systems.com/en.html) 
# https://layers.openembedded.org/layerindex/recipe/166028/
# https://github.com/tano-systems/meta-tanowrt/blob/master/meta-tanowrt/recipes-networking/igh-ethercat/igh-ethercat.bb

PV = "1.6.8+git${SRCPV}"

DESCRIPTION = "IgH EtherCAT Master for Linux"
HOMEPAGE = "http://etherlab.org/download/ethercat"
LICENSE = "GPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
SECTION = "net"

#some small changes to the info transfer from the ioctl to the local structs
SRC_URI = "git://github.com/svhb1000/ethercat.git;protocol=ssh;branch=stable-1.6"
SRCREV = "ffc8440f05f51333b896b868a73ee5b1990ab519"

S = "${WORKDIR}/git"

PACKAGECONFIG ??= "generic"

PACKAGECONFIG[generic] = "--enable-generic,--disable-generic,"
PACKAGECONFIG[8139too] = "--enable-8139too,--disable-8139too,"
PACKAGECONFIG[e100]    = "--enable-e100,--disable-e100,"
PACKAGECONFIG[e1000]   = "--enable-e1000,--disable-e1000,"
PACKAGECONFIG[e1000e]  = "--enable-e1000e,--disable-e1000e,"
PACKAGECONFIG[r8169]   = "--enable-r8169,--disable-r8169,"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

inherit autotools-brokensep pkgconfig module-base

EXTRA_OECONF += "--with-linux-dir=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OECONF += "--with-module-dir=kernel/ethercat"

do_compile:append() {
	oe_runmake modules
}

do_install:append() {
	oe_runmake MODLIB=${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION} modules_install
}

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}"
CONFFILES:${PN} += "${sysconfdir}/config/ethercat"

FILES:${PN} += "/usr/share/bash-completion/completions/ethercat"


