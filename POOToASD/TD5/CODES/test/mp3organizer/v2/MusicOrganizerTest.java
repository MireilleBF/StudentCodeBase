package mp3organizer.v2;

import mp3player.NonAvailableTrackException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MusicOrganizerTest extends mp3organizer.v1.MusicOrganizerTest{

    MusicOrganizer musicOrganizerWithTracks;
    String root = "./resources/audio/v2/";

  @org.junit.jupiter.api.BeforeEach
    protected void setUp() {
        super.setUp();
        musicOrganizerWithTracks = new MusicOrganizer();
    }


    @Test
    void testAddFileInEmptyOrganizerTest() {
        assertEquals(0, musicOrganizerWithTracks.numberOfFiles());
        musicOrganizerWithTracks.addFile(file1);
        assertEquals(1, musicOrganizerWithTracks.numberOfFiles());
        musicOrganizerWithTracks.addFile(file2);
        assertEquals(2, musicOrganizerWithTracks.numberOfFiles());
    }

    @Test
    void testListFileWhenEmptyTest() {
        String[] tracks = musicOrganizerWithTracks.listTracks();
        assertEquals(0,tracks.length);
    }

    @Test
    void testListFile() {
        musicOrganizerWithTracks.addFile(file1);
        String[] tracks = musicOrganizerWithTracks.listTracks();
        assertEquals(1,tracks.length);
        assertEquals(file1, tracks[0]);
        musicOrganizerWithTracks.addFile(file2);
        tracks = musicOrganizerWithTracks.listTracks();
        assertEquals(2,tracks.length);
        assertEquals(file1, tracks[0]);
        assertEquals(0,musicOrganizerWithTracks.getTrackNumber(file1));
        assertEquals(file2, tracks[1]);
        assertEquals(1,musicOrganizerWithTracks.getTrackNumber(file2));
    }

    @Test
    void startPlayingWithBadIndexTest()  {
        assertThrows(NonAvailableTrackException.class, ()-> musicOrganizerWithTracks.startPlaying(1));
    }

    @Test
    void startPlayingSampleWithBadIndexTest() throws NonAvailableTrackException {
        assertThrows(NonAvailableTrackException.class, ()-> musicOrganizerWithTracks.playAndWait(1));
    }

    @Test
    void startPlayingSample() throws NonAvailableTrackException {
        musicOrganizerWithTracks.addFile(file1);
        musicOrganizerWithTracks.playAndWait(1);
        assertEquals(1,musicOrganizerWithTracks.getTrack(0).getNumberOflistens());
        musicOrganizerWithTracks.playTrack(1);
        assertEquals(2,musicOrganizerWithTracks.getTrack(0).getNumberOflistens());
    }

    @Test
    void testFindFile()  {
        assertEquals(-1, musicOrganizerWithTracks.findFirst("CarpeDiem"));
        initOnlyForNames();
        assertEquals(2, musicOrganizerWithTracks.findFirst("CarpeDiem"));
        assertEquals(0, musicOrganizerWithTracks.findFirst("outete"));
    }

    private void initOnlyForNames() {
        musicOrganizerWithTracks.addFile(root+"outete.mp3");
        musicOrganizerWithTracks.addFile(root+"outete-Keen'V.mp3");
        musicOrganizerWithTracks.addFile(root+"CarpeDiem-Keen'V.mp3");
        musicOrganizerWithTracks.addFile(root+"CarpeDiem-McSolaar.mp3");
    }

    @Test
    void testRemoveFile()  {
        //to be sure no exception is raised
        musicOrganizerWithTracks.removeMatchingFiles("outete");
        initOnlyForNames();
        assertEquals(4, musicOrganizerWithTracks.numberOfFiles());
        musicOrganizerWithTracks.removeMatchingFiles("Christophe");
        assertEquals(4, musicOrganizerWithTracks.numberOfFiles());
        musicOrganizerWithTracks.removeMatchingFiles("aar");
        assertEquals(3, musicOrganizerWithTracks.numberOfFiles());
        musicOrganizerWithTracks.removeMatchingFiles("outete");
        assertEquals(1, musicOrganizerWithTracks.numberOfFiles());
        musicOrganizerWithTracks.removeMatchingFiles("Carpe");
        assertEquals(0, musicOrganizerWithTracks.numberOfFiles());
    }

    private  void initializeWithExamples(MusicOrganizer musicOrganizer) {
        final String path = System.getProperty("user.dir") + "/resources/audio/";
        musicOrganizer.addFile(path+"BigBillBroonzy-BabyPleaseDontGo1.mp3");
        musicOrganizer.addFile(path+"BlindBlake-EarlyMorningBlues.mp3");
        musicOrganizer.addFile(path+"BlindLemonJefferson-matchBoxBlues.mp3");
        musicOrganizer.addFile(path+"BlindLemonJefferson-OneDimeBlues.mp3");
    }

    void listen(MusicOrganizer m0, int index, int n){
        Track t = musicOrganizerWithTracks.getTrack(index);
        for (int i =1; i<n+1; i++){
            t.listen();
        }
    }
    private void initialiseList()  {
        initializeWithExamples(musicOrganizerWithTracks);
        //Too Long !! Add a way to modify the number of listen
        listen(musicOrganizerWithTracks,1,4);
        listen(musicOrganizerWithTracks,2,3);
        listen(musicOrganizerWithTracks,3,1);
        /*
        musicOrganizerWithTracks.playAndWait(1);
        musicOrganizerWithTracks.playAndWait(2);
        musicOrganizerWithTracks.playAndWait(3);
        musicOrganizerWithTracks.playAndWait(2);
        musicOrganizerWithTracks.playAndWait(2);
        musicOrganizerWithTracks.playAndWait(1);
        musicOrganizerWithTracks.playAndWait(1);
        musicOrganizerWithTracks.playAndWait(1);
        */
        //Order for listen : 0(0) <3(1)<2(3) <1(4)
        //Order for Artist : 0<1<2=3
        //Order for Title : 0<1<2<3
    }

    @Test
    void extractPreferedTracks() {
        initialiseList();
        Track t1= musicOrganizerWithTracks.getTrack(1);
        assertEquals(4, musicOrganizerWithTracks.numberOfFiles());
        List<Track> preferedTracks= musicOrganizerWithTracks.extractPreferedTracks();
        //to be sure that musicOrganizerWithTracks.tracks was not modified
        assertEquals(t1, musicOrganizerWithTracks.getTrack(1));
        assertEquals(4, musicOrganizerWithTracks.numberOfFiles());
        //prefered
        assertEquals(2,preferedTracks.size());
        assertTrue(preferedTracks.contains(musicOrganizerWithTracks.getTrack(1)));
        assertTrue(preferedTracks.contains(musicOrganizerWithTracks.getTrack(2)));

    }


    @Test
    void testSortByArtist() {
        //Order for Artist : 0<1<2=3
        initialiseList();
        List<Track> sorted= musicOrganizerWithTracks.sortByArtist();
        assertEquals("BigBillBroonzy",sorted.get(0).getArtist());
        assertEquals("BlindBlake",sorted.get(1).getArtist());
        assertEquals("BlindLemonJefferson",sorted.get(2).getArtist());
        assertEquals("BlindLemonJefferson",sorted.get(3).getArtist());
    }

    @Test
    void testSortByTitle() {
        //Order for Title : 0<1<2<3
        initialiseList();
        List<Track> sorted= musicOrganizerWithTracks.sortByArtist();
        assertEquals("BabyPleaseDontGo1",sorted.get(0).getTitle());
        assertEquals("EarlyMorningBlues",sorted.get(1).getTitle());
        assertEquals("matchBoxBlues",sorted.get(2).getTitle());
        assertEquals("OneDimeBlues",sorted.get(3).getTitle());
    }


    @Test
    void testSortByListen()  {
        //Order for listen : 0(0) <3(1)<2(3) <1(4)
        initialiseList();
        List<Track> sorted= musicOrganizerWithTracks.sortByListen();
        assertEquals("BigBillBroonzy",sorted.get(0).getArtist());
        assertEquals("BlindLemonJefferson",sorted.get(1).getArtist());
        assertEquals("BlindLemonJefferson",sorted.get(2).getArtist());
        assertEquals("matchBoxBlues",sorted.get(2).getTitle());
        assertEquals("BlindBlake",sorted.get(3).getArtist());

    }

    @Test
    void testListenPreferredTracks() throws NonAvailableTrackException {
        //Order for listen : 0(0) <3(1)<2(3) <1(4)
        initialiseList();
        List<Track> sorted= musicOrganizerWithTracks.sortByListen();
        int[] played = musicOrganizerWithTracks.randomPlayingPreferedTracks(1);
        assertEquals(2,played.length);
        System.out.println(played[0] + " " + played[1]);
        assertTrue(played[0]==1 || played[0]==2 );
        assertTrue(played[1]==1 || played[1]==2);
    }
    @Test
    void testGetTrackNumber(){
      int i = musicOrganizerWithTracks.getTrackNumber("test");
        assertEquals(-1,i);
        musicOrganizerWithTracks.addFile(file1);
        i = musicOrganizerWithTracks.getTrackNumber(file1);
        System.out.println(musicOrganizerWithTracks);
        assertEquals(0,i);
        musicOrganizerWithTracks.addFile(file2);
        i = musicOrganizerWithTracks.getTrackNumber(file2);
        assertEquals(1,i);
        i = musicOrganizerWithTracks.getTrackNumber(file1);
        assertEquals(0,i);
    }

    @Test
    void testMatching(){
        initialiseList();
        String root = Files4Tests.ROOT_FOR_FILES + "/resources/audio/";

        List<String> matching = musicOrganizerWithTracks.listMatching(Files4Tests.FILE_BLINDBLACK.split("\\.")[0]);
        assertEquals(1,matching.size());
        assertEquals(root+Files4Tests.FILE_BLINDBLACK,matching.get(0));
        matching = musicOrganizerWithTracks.listMatching("Blind");
        assertEquals(3,matching.size());
        matching = musicOrganizerWithTracks.listMatchingArtist("Blind");
        assertEquals(3,matching.size());
        matching = musicOrganizerWithTracks.listMatchingTitle("Blind");
        assertEquals(0,matching.size());
        matching = musicOrganizerWithTracks.listMatchingTitle("Blues");
        assertEquals(3,matching.size());
        matching = musicOrganizerWithTracks.listMatching("Za");
        assertEquals(0,matching.size());
        musicOrganizerWithTracks.addFile(root+Files4Tests.FILE_ZAZ_COULEURS);
        musicOrganizerWithTracks.addFile(root+Files4Tests.FILE_ZAZ_JEVEUX);
        matching = musicOrganizerWithTracks.listMatchingArtist("Za");
        assertEquals(2,matching.size());
        matching = musicOrganizerWithTracks.listMatchingTitle("Za");
        assertEquals(0,matching.size());

    }
}