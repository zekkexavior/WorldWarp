package org.mcstats;

import Raz.WorldWarp.WorldWarp;

public class WWTrack {
    public static Tracker worldLoadsN;
    public static Tracker worldLoadsNE;
    public static Tracker worldLoadsT;
	private static Metrics metrics;

    public static void init(WorldWarp plugin) {
        WWTrack.worldLoadsN = new Tracker("Normal");
        WWTrack.worldLoadsNE = new Tracker("Nether");
        WWTrack.worldLoadsT = new Tracker("The End");
        try {
            WWTrack.metrics = new Metrics(plugin);
            WWTrack.metrics.addCustomData(WWTrack.worldLoadsN);
            WWTrack.metrics.addCustomData(WWTrack.worldLoadsNE);
            WWTrack.metrics.addCustomData(WWTrack.worldLoadsT);
            WWTrack.metrics.start();
        } catch (final Exception e) {
        }
    }
}