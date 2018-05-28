# XFLib

## Unity与科大讯飞第三方SDK整合

### 工具：
- Unity(Version 2018.1.0f2)
- Android Studio(Version 3.1.2)
- 科大讯飞SDK(Version 1130, 2018.5.25)

### 流程
- Build - Make Model 'app'，生成aar文件
- 注意，需要将aar文件通过windows资源浏览打开删除libs文件夹下的classes.jar文件，不删除的话Unity编译apk会因为多份classes.jar文件出错
- 将生产的aar文件放入Unity目录Assets-Plugins-Android下