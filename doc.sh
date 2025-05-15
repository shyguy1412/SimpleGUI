# a shell script to create a java documentation 
# for a processing Library. 
# 
# make changes to the variables below so they 
# fit the structure of your Library

# the package name of your Library
package=net.shy.simplegui;

# source folder location
src=src;

# the destination folder of your documentation
dest=documentation;


# compile the java documentation
javadoc -d $dest -stylesheetfile ./resources/stylesheet.css -sourcepath ${src} ${package} -cp ./lib/*.jar