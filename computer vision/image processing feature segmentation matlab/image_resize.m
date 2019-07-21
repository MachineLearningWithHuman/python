img = imread('zone.gif');

%Modify the code below. Select alternate rows and columns from img using MATLAB matrix slicing
img_resized = imresize(img, 0.5);


[r,c] = size(img_resized)

figure;
imshow(img)
truesize
figure;
imshow(img_resized)
truesize
