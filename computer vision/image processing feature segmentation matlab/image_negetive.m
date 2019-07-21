img = imread('pout.tif');
%Modify the code below. Compute Image Negative
img_neg = imcomplement(img)

figure
imshow(img)
figure
imshow(img_neg)
