package me.deftware.emc_discord_rpc;

import com.github.psnrigner.discordrpcjava.DiscordEventHandler;
import com.github.psnrigner.discordrpcjava.DiscordJoinRequest;
import com.github.psnrigner.discordrpcjava.DiscordRichPresence;
import com.github.psnrigner.discordrpcjava.DiscordRpc;
import com.github.psnrigner.discordrpcjava.ErrorCode;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.main.EMCMod;

public class Main extends EMCMod {

	private DiscordRpc rpc = new DiscordRpc();
	private boolean enabled = true;
	private long start = System.currentTimeMillis() / 1000L;

	@Override
	public void initialize() {
		/* Aristois bot id = 389567324014772244 */
		rpc.init("389567324014772244", new DiscordEventHandler() {

			@Override
			public void ready() {

			}

			@Override
			public void disconnected(ErrorCode errorCode, String message) {

			}

			@Override
			public void errored(ErrorCode errorCode, String message) {

			}

			@Override
			public void joinGame(String joinSecret) {
				// Not supported
			}

			@Override
			public void spectateGame(String spectateSecret) {
				// Not supported
			}

			@Override
			public void joinRequest(DiscordJoinRequest joinRequest) {
				// Not supported
			}

		}, true);
		new Thread(() -> {
			try {
				System.out.println("EMC-Discord-RPC: Setting presence...");
				Thread.sleep(5000L);
				rpc.runCallbacks();
				while (enabled) {
					rpc.updatePresence(getRichPresence());
					Thread.sleep(5000L);
					rpc.runCallbacks();
					Thread.sleep(5000L);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			rpc.shutdown();
		}).start();
	}

	private DiscordRichPresence getRichPresence() {
		DiscordRichPresence discordRichPresence = new DiscordRichPresence();
		discordRichPresence.setState("https://aristois.net/");
		discordRichPresence.setDetails("Minecraft Utility Mod");
		discordRichPresence.setStartTimestamp(start);
		discordRichPresence.setLargeImageKey("aristois_big");
		discordRichPresence.setSmallImageKey("deftware");
		discordRichPresence.setSmallImageText("https://deftware.me/");
		discordRichPresence.setLargeImageText("https://aristois.net/");
		discordRichPresence.setInstance(false);
		return discordRichPresence;
	}

	@Override
	public EMCClientInfo getClientInfo() {
		return new EMCClientInfo("EMC-Discord-RPC", "1.0.0");
	}

	@Override
	public void onEvent(Event event) {

	}

}
