curDir=./
echo begin git add $curDir
git add $curDir
echo begin git pull $curDir
##git pull $curDir
git pull
##date 命令赋值使用$()
echo pls input comment
read comment
echo begin commit -m $comment
git commit -m $comment
echo begin git push $curDir
git push