package ulam;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Ulam {
	
	private Numero[][] m;

	private void riempiMatrice(int n)
	{
		int dim = (int) Math.ceil(Math.sqrt(n));
		m = new Numero[dim][dim];
		Direction dir = Direction.RIGHT;
		int j, i=1;
		j = i;
		int y = dim / 2;
		int x = (dim % 2 == 0) ? y - 1 : y; // shift left for even n's
		while(j <= ((dim * dim) - 1 + i))
		{
			if (j <= n)
				m[y][x] = new Numero(j);
			else
				m[y][x] = null; // pezzo di matrice da non fare

			switch(dir)
			{
				case RIGHT:
					if(x <= (n - 1) && m[y - 1][x] == null && j > i) dir = Direction.UP; break;
				case UP:
					if(m[y][x - 1] == null) dir = Direction.LEFT; break;
				case LEFT:
					if(x == 0 || m[y + 1][x] == null) dir = Direction.DOWN; break;
				case DOWN:
					if(m[y][x + 1] == null) dir = Direction.RIGHT; break;
			}

			switch(dir)
			{
				case RIGHT:	x++; break;
				case UP: 	y--; break;
				case LEFT:	x--; break;
				case DOWN:	y++; break;
			}
			j++;
		}	
	}

	
	void printUlam(int N, int NThreads) throws InterruptedException
	{
		
		BlockingQueue<Numero> bq = new ArrayBlockingQueue(NThreads*10);
		Calcolatore[] operai = new Calcolatore[NThreads];
		
		
		
	}
}


