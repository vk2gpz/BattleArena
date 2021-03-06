package mc.alk.arena.controllers.messaging;

import mc.alk.arena.competition.events.Event;
import mc.alk.arena.objects.ArenaPlayer;
import mc.alk.arena.objects.MatchState;
import mc.alk.arena.objects.messaging.AnnouncementOptions;
import mc.alk.arena.objects.messaging.Channel;
import mc.alk.arena.objects.messaging.Channels;
import mc.alk.arena.objects.messaging.EventMessageHandler;
import mc.alk.arena.objects.teams.ArenaTeam;
import mc.alk.arena.util.Log;

import java.util.Collection;
import java.util.Set;


public class EventMessager {
	EventMessageHandler impl;
	final AnnouncementOptions bos;
	boolean silent = false;

	public EventMessager(Event event){
		this.impl = new EventMessageImpl(event);
		this.bos = event.getParams().getAnnouncementOptions();
	}

	protected Channel getChannel(MatchState state) {
		if (silent) return Channels.NullChannel;
		return bos != null && bos.hasOption(false,state) ? bos.getChannel(false,state) :
			AnnouncementOptions.getDefaultChannel(false,state);
	}

	public void setMessageHandler(EventMessageHandler handler) {
		this.impl = handler;
	}

	public EventMessageHandler getMessageHandler() {
		return impl;
	}

	public void sendCountdownTillEvent(int seconds) {
		try{impl.sendCountdownTillEvent(getChannel(MatchState.ONCOUNTDOWNTOEVENT), seconds);}
		catch(Exception e){Log.printStackTrace(e);}
	}

	public void sendEventStarting(Collection<ArenaTeam> teams) {
		try{impl.sendEventStarting(getChannel(MatchState.ONSTART), teams);}catch(Exception e){Log.printStackTrace(e);}
	}

	public void sendEventOpenMsg() {
		try{impl.sendEventOpenMsg(getChannel(MatchState.ONOPEN));}catch(Exception e){Log.printStackTrace(e);}
	}

	public void sendEventCancelledDueToLackOfPlayers(Set<ArenaPlayer> competingPlayers) {
		try{impl.sendEventCancelledDueToLackOfPlayers(getChannel(MatchState.ONCANCEL), competingPlayers);
	}catch(Exception e){Log.printStackTrace(e);}
	}

//	public void sendTeamJoinedEvent(Team t) {
//		try{impl.sendTeamJoinedEvent(getChannel(MatchState.ONJOIN),t);}catch(Exception e){Log.printStackTrace(e);}
//	}

	public void sendEventCancelled(Collection<ArenaTeam> teams) {
		try{impl.sendEventCancelled(getChannel(MatchState.ONCANCEL), teams);}catch(Exception e){Log.printStackTrace(e);}
	}

	public void sendCantFitTeam(ArenaTeam t) {
		try{impl.sendCantFitTeam(t);}catch(Exception e){Log.printStackTrace(e);}
	}

	public void sendWaitingForMorePlayers(ArenaTeam t, int remaining) {
		try{ impl.sendWaitingForMorePlayers(t, remaining);}catch(Exception e){Log.printStackTrace(e);}
	}
	public void setSilent(boolean silent){
		this.silent = silent;
	}

	public void sendEventVictory(Collection<ArenaTeam> victors, Collection<ArenaTeam> losers) {
		try{impl.sendEventVictory(getChannel(MatchState.ONVICTORY), victors,losers);}catch(Exception e){Log.printStackTrace(e);}
	}

	public void sendEventDraw(Collection<ArenaTeam> drawers, Collection<ArenaTeam> losers) {
		try{impl.sendEventDraw(getChannel(MatchState.ONVICTORY), drawers, losers);}catch(Exception e){Log.printStackTrace(e);}
	}

	public void sendTeamJoined(ArenaTeam team) {
		try{impl.sendTeamJoinedEvent(getChannel(MatchState.ONJOIN),team);}catch(Exception e){Log.printStackTrace(e);}
	}


//	public void sendEventJoin(Team team, ArenaPlayer player) {
//		try{impl.sendEventJoin(getChannel(MatchState.ONJOIN), drawers, losers);}catch(Exception e){Log.printStackTrace(e);}
//	}

}
