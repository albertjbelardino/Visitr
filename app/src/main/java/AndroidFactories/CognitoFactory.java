package AndroidFactories;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoFactory {

    public Context context;
    public String userPoolId = "us-east-1_lneOm8LJn";
    public String clientId = "240p4ovqkbip65piplamaalhke";
    public String clientSecret = "1mi3pg264v0gnhb83m23gs33tc3h79u16sftatu9hu23qvon9j2k";
    public Regions cognitoRegion = Regions.US_EAST_1;

    public CognitoFactory(Context context) {
        this.context = context;
    }

    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);
    }
}
