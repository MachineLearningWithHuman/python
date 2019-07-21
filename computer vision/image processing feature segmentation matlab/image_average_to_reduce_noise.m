load imgarray  %this cell array consists of 20 images of the same subject

%Average of 2 images. Modify this to work for 20 images using for loop
%img_avg = (imgarray{1} + imgarray{2})/2  - This would not work
x=double(imgarray{1});
s=20;
for c=2:s
    x=x+double(imgarray{c});
end

    
%As image data is uint8. Addition of intensities may exceed the maximum intensity each pixel can hold

%You need to convert it to double type and perform the operation and then convert back to uint8 datatype
%img_avg = uint8(double(imgarray{1} + imgarray{2})/2);
img_avg=uint8((x/20));

%Do not modify this code
figure;
subplot(1,2,1)
imshow(imgarray{1})
title('Sample Image from  the image array');
subplot(1,2,2)
imshow(img_avg)
title('Averaged Image');