package br.com.timer.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

public class Util {

	/**
	 * A partir de um long time, retorna no formato 0:00:00
	 * 
	 * @param longTime
	 * @return format hours
	 */
	public static String formatHours(long longTime) {
		long diffSeconds = (longTime / 1000) % 60;
        long diffMinutes = (longTime / (60 * 1000)) % 60;
        long diffHours = (longTime / (60 * 60 * 1000));
               
        StringBuilder builder = new StringBuilder();
        
        if (diffHours >= 10) {
        	builder.append(diffHours).append(":");
        } else if (diffHours > 0) {
        	builder.append("0").append(diffHours).append(":");
        } else {
        	builder.append("00:");
        }
        
        if(diffMinutes < 10) {
        	builder.append(0);
        }
        builder.append(diffMinutes).append(":");
    	
    	if(diffSeconds < 10) {
    		builder.append(0);
    	}
    	builder.append(diffSeconds);
    
    	return builder.toString();
	}
	
	public static String formatHours(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); 
		return formatter.format(date);
	}
	
	/**
	 * Retorna a data do primeiro dia útil (segunda feira) da semana de uma data qualquer
	 *  
	 * @param date uma data qualquer
	 * @return data da segunda feira da data passada por parâmetro.
	 */
	public static String getStartDayOfWeek(Date date) {
		throw new UnsupportedOperationException("Falta implementar mudança para java 8");
		/*DateTime dateTime = new DateTime(date);
		int today = dateTime.getDayOfWeek();
		int start = 0;
		
		if (today > 1) {
			start = today -  1;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return dateFormat.format(dateTime.minusDays(start).toDate());*/
	}
	
	/**
	 * Retorna a data do útilmo dia útil (sexta-feira) da semana de uma data qualquer
	 *  
	 * @param date uma data qualquer
	 * @return data da sexta feira da data passada por parâmetro 
	 */
	public static String getEndDayOfWeek(Date date) {
		throw new UnsupportedOperationException("Falta implementar mudança para java 8");
		/*DateTime dateTime = new DateTime(date);
		int today = dateTime.getDayOfWeek();
		int end = 0;
		
		if (today < 5) {
			end = 5 - today;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return dateFormat.format(dateTime.plusDays(end).toDate());*/
	}

	/**
	 * Retorna a diferença em horas de duas datas.
	 * 
	 * @param o1 data de entrada
	 * @param o2 data de saida
	 * @return diferença em horas entre a data de entrada e saida no formato HH:mm.
	 */
	public static String getTotalTime(Date o1, Date o2) {
		long total = o2.getTime() - o1.getTime();
		return formatHours(total);
	}

	/**
	 * Retorna a diferença em horas de duas datas.
	 * 
	 * @param o1 data de entrada
	 * @param o2 data de saida
	 * @return diferença em horas entre a data de entrada e saida no formato HH:mm.
	 */
	public static long getMilisTime(LocalDateTime o1, LocalDateTime o2) {
		Duration duration = Duration.between(o1, o2);
		return duration.toMillis();
	}
	
	public static long getMilisTime(LocalDateTime o1) {
		return getMilisTime(o1, LocalDateTime.now());
	}
	
	public static long addDateToMilis(long milis) {
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().minusMillis(milis).toEpochMilli();
	}

	public static boolean isVazio(String o) {
		if (o == null || o.trim().equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNotNullOrEmpty(Object o) {
		return !isNullOrEmpty(o);
	}
	public static boolean isNullOrEmpty(Object o) {
		if (o == null) {
			return true;
		}
		
		if (o instanceof String) {
			String s = String.class.cast(o);
			
			return s.trim().equals("");
		}
		if (o instanceof Collection<?>) {
			Collection<?> c = Collection.class.cast(o);
			
			if (c.isEmpty()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Extrai uma hora de um tipo {@link Date}
	 * @param date data
	 * @return hora no formato HH:mm
	 */
	public static String getHours(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		
		return dateFormat.format(date);
	}
	
	/**
	 * Retorna uma string de uma data no formato dd/MM/yyyy extraida da data passada por parâmetro
	 * 
	 * @param date {@link Date}
	 * @return data no formato dd/MM/yyyy
	 */
	public static String getDateFormatted(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date);
	}

	/**
	 * Retorna uma string de uma data no formato dd/MM/yyyy extraida da data passada por parâmetro
	 * 
	 * @param date {@link DateTime}
	 * @return data no formato dd/MM/yyyy
	 */
	public static String getDateFormatted(LocalDateTime date) {
		if (date == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		return formatter.format(date);
	}

	/**
	 * Retorna o dia do ano de uma data 1-366.
	 * 
	 * @param date {@link Date}
	 * @return retorna o dia do ano.
	 */
	public static int getDayOfYear(Date date) {
		LocalDateTime time = LocalDateTime.from(date.toInstant());
		return time.getDayOfYear();
	}

	/**
	 * Retorna o dia da semana baseado na data de entrada
	 * 
	 * @param date data de entrada
	 * @return baseado na data de entrada pode retornar: Segunda, Terça, Quarta, Quinta, Sexta, Sábado, Domingo
	 */
	public static String getWeekDay(LocalDateTime date) {
		switch(date.getDayOfWeek()) {
			case MONDAY: return "Segunda"; 
			case TUESDAY: return "Terça"; 
			case WEDNESDAY: return "Quarta"; 
			case THURSDAY: return "Quinta"; 
			case FRIDAY: return "Sexta"; 
			case SATURDAY: return "Sábado"; 
			case SUNDAY: return "Domingo"; 
		}
		
		return null;
	}

	/**
	 * retorna a data formatada dd/MM/yyyy de um dia da semana ({@link EDia}) e uma data qualquer repesentando uma semana qualquer.
	 * 
	 * @param dia {@link EDia}
	 * @param dayOfWeek data de uma semana qualquer
	 * @return data do dia formatado dd/MM/yyyy
	 */
	/*public static String getDateFormatted(EDia dia, Date dayOfWeek) {
		return getDateFormatted(dia, LocalDateTime.from(dayOfWeek.toInstant()));
	}*/
	/**
	 * retorna a data formatada dd/MM/yyyy de um dia da semana ({@link EDia}) e uma data qualquer repesentando uma semana qualquer.
	 * 
	 * @param dia {@link EDia}
	 * @param dayOfWeek data de uma semana qualquer
	 * @return data do dia formatado dd/MM/yyyy
	 */
	/*public static String getDateFormatted(EDia dia, LocalDateTime dayOfWeek) {
		LocalDateTime localDate = LocalDateTime.from(dayOfWeek);
		
		TemporalAdjuster ajustadorParaProximaSexta = TemporalAdjusters.next(DayOfWeek.of(dia.getDayOfWeek()));
		LocalDateTime proximaSexta = LocalDateTime.now().with(ajustadorParaProximaSexta);
		
		return getDateFormatted(localDate);
	}*/
}