### Константин Богданоски, 161048
> ### Konstantin Bogdanoski

***

За да го завршам опционалното барање, ја одбрав сферата на партиционирање кај базите на податоци.
> To complete the optional task, I have decided to use Partitioning as the theme of the task. 

Бидејќи бројот на нарачки (`pizza_order`) во нашата база е голем (повеќе од 1,000,000 нарачки), ние можеме да извршиме партиционирање на табелата со атрибутот `date_created`, така што ќе можеме да ја поделиме табелата на партиции кои се правени според годината (можеме и да ги партиционираме и според денови/недели/месеци).
> The goal is because there are a lot of orders (`pizza_order`) in our DB (greater than 1,000,000), we can partition the table based on the `date_created` attribute and separate the orders into different partitions represented by the year/month/week/day in which they were created. 

Направени се 3 партиции за годините 2013, 2014 и 2015 како примери за партиционирање.
> As an example, I have created 3 partitions for the years 2013, 2014 and 2015 respectively.

***

Напредни Бази на Податоци, ФИНКИ, Скопје, Северна Македонија, 2020
> Advanced Data Bases, FCSE, Skopje, North Macedonia, 2020
