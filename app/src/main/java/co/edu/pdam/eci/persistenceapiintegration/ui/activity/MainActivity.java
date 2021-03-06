package co.edu.pdam.eci.persistenceapiintegration.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.pdam.eci.persistenceapiintegration.R;
import co.edu.pdam.eci.persistenceapiintegration.adapter.TeamsAdapter;
import co.edu.pdam.eci.persistenceapiintegration.data.DBException;
import co.edu.pdam.eci.persistenceapiintegration.data.OrmModel;
import co.edu.pdam.eci.persistenceapiintegration.data.dao.TeamDao;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import co.edu.pdam.eci.persistenceapiintegration.network.NetworkException;
import co.edu.pdam.eci.persistenceapiintegration.network.RequestCallback;
import co.edu.pdam.eci.persistenceapiintegration.network.RetrofitNetwork;


public class MainActivity
    extends AppCompatActivity
{

    private OrmModel ormModel;
    private RecyclerView recyclerView;
    private TeamsAdapter teamsAdapter;
    private List<Team> teams;
    private RetrofitNetwork retrofitNetwork;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ormModel = new OrmModel();
        ormModel.init(this);
        final TeamDao teamDao = ormModel.getTeamDao();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //Network request code goes here
                    retrofitNetwork = new RetrofitNetwork();
                    retrofitNetwork.getTeams(new RequestCallback<List<Team>>() {
                        @Override
                        public void onSuccess(List<Team> response) {
                            for(Team t:response){
                                try {
                                    teamDao.create(t);

                                } catch (DBException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailed(NetworkException e) {
                            e.printStackTrace();
                        }

                    });
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
        } );

        try {
            teams = teamDao.getAll();
            configureRecyclerView();


        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void configureRecyclerView() throws DBException {
        teamsAdapter = new TeamsAdapter(teams,this);
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter(teamsAdapter);
    }
}
