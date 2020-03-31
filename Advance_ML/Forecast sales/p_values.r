data <- Training.Data.Sets...NEWDATA

#we are finding usefull variables in this dataset from backselection regression

data$s.no<-NULL #we are finding variable relation

#1st regression method

model_1<-lm(EQ~.,data=data)
summary(model_1)

data$Digital_Working_cost<-NULL
model_2<-lm(EQ~.,data=data)
summary(model_2)

data$Any_Feat_pct_ACV<-NULL

model_3<-lm(EQ~.,data=data)
summary(model_3)

data$Print_Working_Cost.Ads50<-NULL

model_4<-lm(EQ~.,data=data)
summary(model_4)

data$Trade_Invest<-NULL

model_5<-lm(EQ~.,data=data)
summary(model_5)

data$Any_Disp_pct_ACV<-NULL

model_6<-lm(EQ~.,data=data)
summary(model_6)

data$Competitor4_RPI<-NULL
model_7<-lm(EQ~.,data=data)
summary(model_7)


data$Competitor2_RPI<-NULL

model_8<-lm(EQ~.,data=data)
summary(model_8)

data$Social_Search_Working_cost<-NULL
data$OOH_Impressions<-NULL
data$Brand_Equity<-NULL
data$Est_ACV_Selling<-NULL

model_8<-lm(EQ~.,data=data)
summary(model_8)

data$RPI_Subcategory<-NULL
data$Competitor1_RPI<-NULL
data$Competitor3_RPI<-NULL

model_9<-lm(EQ~.,data=data)
summary(model_9)

data$Avg_no_of_Items<-NULL
data$Fuel_Price<-NULL

model_10<-lm(EQ~.,data=data)
summary(model_10)

data$Digital_Impressions<-NULL
data$Print_Impressions.Ads40<-NULL
data$Digital_Impressions_pct<-NULL
data$Any_Promo_pct_ACV<-NULL
data$EQ_Base_Price<-NULL
data$pct_ACV<-NULL
data$TV_GRP<-NULL

model_10<-lm(EQ~.,data=data)
summary(model_10)


data$Median_Temp<-NULL
data$CCFOT<NULL
data$Avg_EQ_Price<-NULL

model_11<-lm(EQ~.,data=data)
summary(model_11)


data$SOS_pct<-NULL
data$RPI_Category<-NULL

model_12<-lm(EQ~.,data=data)
summary(model_12)
#we are not satisfied but linear assumption provides this value only

str(data)
