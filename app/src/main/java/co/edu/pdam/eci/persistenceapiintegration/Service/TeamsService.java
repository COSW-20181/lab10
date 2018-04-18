package co.edu.pdam.eci.persistenceapiintegration.Service;

import java.util.List;

import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import retrofit2.http.GET;

/**
 * Created by 2105409 on 4/18/18.
 */

public interface TeamsService {

    @GET( "teams.json" )
    retrofit2.Call<List<Team>> getTeamsList();

}
