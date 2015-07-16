package br.com.recidev.gamethis.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import br.com.recidev.gamethis.R;
import br.com.recidev.gamethis.adapter.RankingAdapter;
import br.com.recidev.gamethis.dominio.Ranking;
import br.com.recidev.gamethis.util.GerenciadorSessao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RankingActivity extends Activity {

	
	private RankingAdapter rankingAdapter;
	private ListView rankingListView;
	
	GerenciadorSessao sessao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
		
		String dadosRequisicao = getIntent().getStringExtra("dadosRequisicao");
		
		ArrayList<Ranking> arrayRanking = new ArrayList<Ranking>();
		Gson gson = new Gson();
		arrayRanking = gson.fromJson(dadosRequisicao, new TypeToken<ArrayList<Ranking>>(){}.getType());
		
		rankingAdapter = new RankingAdapter(this, arrayRanking);
		rankingListView = (ListView) findViewById(R.id.rankingListView);
		rankingListView.setAdapter(rankingAdapter);
	}

	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ranking, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
