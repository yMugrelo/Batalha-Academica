import greenfoot.*;
import java.util.HashMap;

/**
 * SoundManager - o ÚNICO lugar do jogo que toca sons.
 *
 * Regras de segurança (para o jogo NUNCA depender de áudio):
 *   - só toca arquivos que estão na lista AVAILABLE (sons/ ausentes = silêncio)
 *   - se um som der erro ao carregar, o áudio é desligado e o jogo segue
 *   - a música só começa depois da primeira tecla ou clique do jogador:
 *     navegadores bloqueiam áudio antes de uma interação (HTML5)
 *   - tecla M liga/desliga o som a qualquer momento
 *
 * QUANDO ADICIONAR UM SOM: coloque o .wav em sounds/ e acrescente
 * o nome em AVAILABLE (ex.: "menu.wav").
 */
public class SoundManager
{
    /** Sons que realmente existem dentro de sounds/. */
    private static final String[] AVAILABLE = {
        // Exemplo: "menu.wav",
    };

    /** Todos os sons que o jogo sabe usar (para o relatório de ausentes). */
    private static final String[] EXPECTED = {
        "menu.wav", "battle.wav", "attack.wav", "victory.wav", "defeat.wav"
    };

    private static final int MUSIC_VOLUME = 55;
    private static final int EFFECT_VOLUME = 80;

    private static GreenfootSound music;         // música tocando agora
    private static String musicName;             // nome da música tocando agora
    private static String wantedMusic;           // música que a tela atual quer
    private static HashMap<String, GreenfootSound> effects = new HashMap<String, GreenfootSound>();

    private static boolean userInteracted = false;   // já houve tecla/clique?
    private static boolean muted = false;
    private static boolean disabled = false;         // um erro de áudio desliga tudo

    // ===================== Música =====================

    /**
     * Pede uma música em loop ("menu" ou "battle").
     * Se já estiver tocando, continua de onde está (não reinicia).
     */
    public static void playMusic(String name)
    {
        wantedMusic = name;
        startWantedMusic();
    }

    /** Para a música (ex.: fim da batalha). */
    public static void stopMusic()
    {
        wantedMusic = null;
        stopCurrentMusic();
    }

    // ===================== Efeitos =====================

    /**
     * Toca um efeito curto ("attack", "victory", "defeat").
     */
    public static void playEffect(String name)
    {
        String file = name + ".wav";
        if (!canPlay() || !isAvailable(file))
        {
            return;
        }
        try
        {
            GreenfootSound sound = effects.get(file);
            if (sound == null)
            {
                sound = new GreenfootSound(file);
                sound.setVolume(EFFECT_VOLUME);
                effects.put(file, sound);
            }
            sound.play();
        }
        catch (RuntimeException e)
        {
            disabled = true;   // áudio com problema: seguimos em silêncio
        }
    }

    // ===================== Controle =====================

    /**
     * Chamado pelo BaseWorld a cada tecla ou clique.
     * Na primeira vez, libera o áudio e começa a música pedida.
     */
    public static void onUserInput()
    {
        if (!userInteracted)
        {
            userInteracted = true;
            startWantedMusic();
        }
    }

    /** Liga/desliga o som (tecla M). */
    public static void toggleMute()
    {
        muted = !muted;
        if (muted)
        {
            stopCurrentMusic();
        }
        else
        {
            startWantedMusic();
        }
    }

    public static boolean isMuted()
    {
        return muted;
    }

    /** O Greenfoot foi pausado (botão Pause): pausa a música. */
    public static void pause()
    {
        if (music != null)
        {
            music.pause();
        }
    }

    /** O Greenfoot voltou a rodar: continua a música. */
    public static void resume()
    {
        if (music != null && canPlay())
        {
            music.playLoop();
        }
    }

    /**
     * Mostra no Terminal quais sons ainda faltam.
     * Botão direito na classe SoundManager > printMissingSounds().
     */
    public static void printMissingSounds()
    {
        for (String file : EXPECTED)
        {
            if (!isAvailable(file))
            {
                System.out.println("Asset ausente: sounds/" + file);
            }
        }
    }

    // ===================== Internos =====================

    private static boolean canPlay()
    {
        return userInteracted && !muted && !disabled;
    }

    private static boolean isAvailable(String file)
    {
        for (String available : AVAILABLE)
        {
            if (available.equals(file))
            {
                return true;
            }
        }
        return false;
    }

    private static void startWantedMusic()
    {
        if (!canPlay() || wantedMusic == null)
        {
            return;
        }
        if (wantedMusic.equals(musicName) && music != null && music.isPlaying())
        {
            return;   // já está tocando a música certa
        }

        stopCurrentMusic();
        String file = wantedMusic + ".wav";
        if (!isAvailable(file))
        {
            return;
        }
        try
        {
            music = new GreenfootSound(file);
            music.setVolume(MUSIC_VOLUME);
            music.playLoop();
            musicName = wantedMusic;
        }
        catch (RuntimeException e)
        {
            disabled = true;
            music = null;
        }
    }

    private static void stopCurrentMusic()
    {
        if (music != null)
        {
            music.stop();
        }
        music = null;
        musicName = null;
    }
}
