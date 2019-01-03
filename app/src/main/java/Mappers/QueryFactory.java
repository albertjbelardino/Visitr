package Mappers;

import android.content.Context;
import android.widget.AdapterView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import Models.TourDO;
import Objects.FullTour;

public class QueryFactory {

    public static void queryGenre(final Context context, final AdapterView<?> parent,
                                        final DynamoDBMapper dynamoDBMapper, final int position) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String genre_query = parent.getItemAtPosition(position).toString();

                TourDO tourDO = new TourDO();
                tourDO.set_genre(genre_query);

                Condition rangeKeyCondition = new Condition()
                        .withComparisonOperator(ComparisonOperator.CONTAINS)
                        .withAttributeValueList(new AttributeValue().withS(genre_query));

                DynamoDBQueryExpression<TourDO> queryExpression = new DynamoDBQueryExpression<TourDO>()
                        .withHashKeyValues(tourDO)
                        .withIndexName("GenreIndex")
                        .withConsistentRead(false);

                PaginatedList<TourDO> result = dynamoDBMapper.query(TourDO.class, queryExpression);
                FullTour[] tour_array = new FullTour[10];

                for (int i = 0; i < result.size(); i++)
                    tour_array[i] = new FullTour(result.get(i));
            }
        }).start();

        ;
    }
}
