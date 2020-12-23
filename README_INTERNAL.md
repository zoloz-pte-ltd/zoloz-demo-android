# 内部说明
zoloz android demo仓库，现已经和开源github地址
https://github.com/zoloz-pte-ltd/zoloz-demo-android
进行了aci流水线集成。

流水线会在合并master后触发，触发后，会自动同步最新的仓库中的代码以及对应的git history.
开源仓库的任何代码同步都**必须通过流水线同步，不允许手动同步**

开源发布会去掉aci文件以及这个内部readme。
如果只是修改这些文件同步会失败，但是不影响，忽略就好。

流水线详情请自行阅读aci代码。