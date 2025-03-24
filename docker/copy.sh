#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
#echo "begin copy sql "
#cp ../sql/staitech-cloud.sql ./mysql/db
#cp ../sql/nacos_config.sql ./mysql/db

# copy html
#echo "begin copy html "
#cp -r ../staitech-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy staitech-gateway "
cp ../staitech-gateway/target/staitech-gateway.jar ./staitech/gateway/jar

echo "begin copy staitech-auth "
cp ../staitech-auth/target/staitech-auth.jar ./staitech/auth/jar

echo "begin copy staitech-modules-system "
cp ../staitech-modules/staitech-system/target/staitech-modules-system.jar ./staitech/modules/system/jar

echo "begin copy staitech-modules-file "
cp ../staitech-modules/staitech-file/target/staitech-modules-file.jar ./staitech/modules/file/jar

echo "begin copy staitech-modules-job "
cp ../staitech-modules/staitech-job/target/staitech-modules-job.jar ./staitech/modules/job/jar


#echo "begin copy staitech-modules-openslide "
#cp ../staitech-modules/staitech-openslide/target/staitech-modules-openslide.jar ./staitech/modules/openslide/jar