#confirmed_cases
#Question_1
  #Query of confirmed cases in world in january
SELECT
  country_region
FROM (
  SELECT
    country_region,
    ( _1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) AS confirmed_january
  FROM
    `bigquery-public-data.covid19_jhu_csse.confirmed_cases`
  WHERE
    (_1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) > 0
  GROUP BY
    1,
    2
  ORDER BY
    confirmed_january DESC)
GROUP BY
  country_region


#Question_2

  #Query of confirmed cases in world in january
SELECT
  country_region,
  confirmed_january AS cases
FROM (
  SELECT
    country_region,
    ( _1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) AS confirmed_january
  FROM
    `bigquery-public-data.covid19_jhu_csse.confirmed_cases`
  WHERE
    (_1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) > 0
  GROUP BY
    1,
    2
  ORDER BY
    confirmed_january DESC)
GROUP BY
  country_region,
  cases
ORDER BY
  cases DESC
  LIMIT 5;


 #Question 3
 
 SELECT
  
    country_region,province_state ,
    ( _1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) AS confirmed_january_cases_china
  FROM
    `bigquery-public-data.covid19_jhu_csse.confirmed_cases`
  WHERE
    (_1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) > 0 And country_region = "China"
  GROUP BY
    1,
    2,
    3
  ORDER BY
    3 DESC


	#Question4

	#Query of confirmed cases in world in january

  SELECT
  province_state
    FROM
    `bigquery-public-data.covid19_jhu_csse.confirmed_cases`
  WHERE
    (_1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) > 500 And country_region = "China"
  ORDER BY
    (_1_22_20 + _1_23_20+ _1_24_20+ _1_25_20+ _1_26_20+ _1_27_20+ _1_28_20+ _1_29_20+ _1_30_20+_1_31_20 ) DESC