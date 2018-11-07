package hk.hku.cs.comp7506_project.News;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import hk.hku.cs.comp7506_project.R;

public class NewsFeed extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        mRecyclerView = findViewById(R.id.news_recycle_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NewsAdapter(generateData());
        mRecyclerView.setAdapter(mAdapter);
    }

    private NewsObject[] generateData(){
        NewsObject[] data = new NewsObject[3];

        data[0] = new NewsObject("Mainland Chinese tourist influx to Tung Chung sparks resident complaints",
                "Hong Kong’s Tourism Commission had raised the concerns to the Guangdong Tourism Bureau and urged for better coordination of the tour groups, the Post has learned.\n" +
                        "\n" +
                        "On a radio show on Monday, a Tung Chung caller, who went only by her surname Cheung, said the problem had worsened since the bridge opened on October 23.\n" +
                        "\n" +
                        "“There is no space for us in Tung Chung. The shops are so crowded with tourists, who sit everywhere … I have to escape on Sundays now,” she said.\n" +
                        "\n", null);

        data[1] = new NewsObject("Golfers tee up in Cup of Kindness for Operation Santa Claus",
                "“We hope that in future we can have similar events built on our relationship,” Chiverton added.\n" +
                        "\n" +
                        "The charity golf event was capped off with the presentation of a cheque of HK$1 million to Operation Santa Claus by Arnold Wong Chi-chiu, a former club captain and its current charity committee chairman.\n" +
                        "\n" +
                        "Home of Loving Faithfulness, a residential care home for children and adults with mental and physical disabilities, was also a beneficiary.\n" +
                        "\n" +
                        "Pui Yee, a member of the home’s board of trustees, said she was thankful that the Cup of Kindness had been raising money for the charity for nearly 30 years.",
                null);

        data[2] = new NewsObject("Green group aims to revive rural area neglected after decades of squabbles",
                "The native Romer’s tree frog and Hong Kong paradise fish have also been documented there.\n" +
                        "\n" +
                        "“Our foremost goal is to ensure that the ecological value does not decline any further,” Sin said.\n" +
                        "\n" +
                        "The NGO received HK$8.5 million (US$1.1 million) from the government’s Environment and Conservation Fund earlier this year to implement a two-year conservation management agreement on some 11.5 hectares (28 acres). The first stage of work – baseline ecosystem studies – began in April.\n" +
                        "\n" +
                        "Longer-term measures include building artificial ponds and marshes, clearing the area of traps and invasive species such as the aggressive “mile-a-minute weed”, and replanting aquatic plants.\n" +
                        "\n" +
                        "It will also bring back some organic farming with the help of some villagers.", null);

        return data;
    }
}
