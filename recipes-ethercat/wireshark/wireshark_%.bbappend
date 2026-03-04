# Split dumpcap into its own package so it can be installed independently
# without pulling in the full wireshark installation.

PACKAGE_BEFORE_PN += "wireshark-dumpcap"

FILES:wireshark-dumpcap = "${bindir}/dumpcap"

RDEPENDS:wireshark-dumpcap = "libpcap libcap"
