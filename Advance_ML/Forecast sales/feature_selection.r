#feature selection

data<-Training.Data.Sets...NEWDATA

baseline_model<-lm(EQ~Social_Search_Impressions+EQ_Category+EQ_Subcategory,data = data)
step(baseline_model)

full_model<-lm(EQ~.,data = data)
step(full_model)

#result according to step function package faraway
#based on AIC value

#ols package
k<-olsrr::ols_step_best_subset(baseline_model)
plot(k)


#
k<-olsrr::ols_step_forward_p(baseline_model)
plot(k)

olsrr::ols_step_both_p(baseline_model)
