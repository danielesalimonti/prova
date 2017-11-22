package ulam;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


enum Direction {
	RIGHT, UP, LEFT, DOWN;
}

// CLASSE DI UTILITA'
class Numero {
	private int valore;
	private boolean primo;
	private boolean calcolato;
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public Numero(int v) {
		this.valore = v;
		this.calcolato = false;
	}
	
	public int getValore() { return this.valore; }
	public boolean isPrimo() { return this.primo; }
	
	private boolean ePrimo()
	{
		if (valore <= 1)
			return false;
		if (valore == 2)
			return true;
		if (valore % 2 == 0)
			return false;
		for (int i=3; i<=Math.sqrt(valore); i+=2)
			if (valore % i == 0)
				return false;
		return true;
	}
	
	public void valutaPrimo()
	{
		lock.lock();
		this.primo = ePrimo();
		this.calcolato = true;
		condition.signalAll();
		lock.unlock();
	}
	
	public void attendiCalcolo()
	{
		lock.lock();
		try{
			while(!calcolato){
				condition.await();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		finally{
			lock.unlock();
		}
	}
}


// THREAD CALCOLATORE
class Calcolatore extends Thread {
	
	private BlockingQueue<Numero> numeriDaCalcolare;
	
	public Calcolatore(BlockingQueue<Numero> numeri) {
		this.numeriDaCalcolare = numeri;
	}
	
	@Override
	public void run() {
		
		while(true){
			
			try{
				Numero corrente = numeriDaCalcolare.take();
				if (corrente.getValore() != -1){
					
				corrente.valutaPrimo();
				
				}
				else break;
			}catch( Exception ex){
				ex.printStackTrace();
			}
			
		}
		
	}
	
}




// CLASSE SPIRALE ULAM
