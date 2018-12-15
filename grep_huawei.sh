#!/bin/sh
cat oui.txt | grep '(hex)' | grep 'HUAWEI' | cut -f1 -d' ' > huawei.txt
