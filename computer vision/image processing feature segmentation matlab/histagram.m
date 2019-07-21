img = imread('cameraman.tif');
%evaluate histogram of the image and store it in the variable - histogram
histogram=imhist(img);

plot(histogram)