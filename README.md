# mvvm-example
Jetpack全家桶实战  
使用kotlin编写，涵盖了ViewModel+LiveData+DataBinding+Room+Paging等组件的使用  

组件化结构  
Manifest合并，子模块Application通过meta-data进行反射调用，执行各生命周期方法  

app壳，base、common基础lib  
user、call、contact、music为业务子模块  
version_plugin为buildSrc统一版本和依赖管理  


