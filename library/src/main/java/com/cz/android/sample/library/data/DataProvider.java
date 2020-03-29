package com.cz.android.sample.library.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.SparseArray;

import androidx.annotation.IntRange;
import androidx.annotation.Keep;

import com.cz.android.sample.library.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * @author Created by cz
 * @date 2020-01-30 18:20
 * @email bingo110@126.com
 */
@Keep
public class DataProvider {
    /**
     * an random object
     */
    public static final Random RANDOM=new Random();
    /**
     * temp name array
     */
    public static final char[] nameTempArray=new char[2];
    /**
     * material color array attribute
     */
    public static final int COLOR_RED=0;
    public static final int COLOR_PINK=1;
    public static final int COLOR_PURPLE=2;
    public static final int COLOR_DEEP_PURPLE=3;

    public static final int COLOR_INDIGO=4;
    public static final int COLOR_blue=5;
    public static final int COLOR_light_Blue=6;
    public static final int COLOR_CYAN=7;
    //青色色系
    public static final int COLOR_TEAL=8;
    public static final int COLOR_LIGHT_GREEN=9;
    //绿黄色
    public static final int COLOR_LIME=10;
    public static final int COLOR_YELLOW=11;

    public static final int COLOR_AMBER=12;
    public static final int COLOR_ORANGE=13;
    public static final int COLOR_DEEP_ORANGE=14;
    public static final int COLOR_BROWN=15;

    public static final int COLOR_GREY=16;
    public static final int COLOR_BLUE_GREY=17;
    /**
     * 缓存的颜色数组列
     */
    private static final SparseArray<int[]> colorSparseArray=new SparseArray<>();
    /**
     * 颜色引用数据
     */
    private static final int[] COLOR_ATTR_ARRAY={
            R.array.redTextValues,
            R.array.pinkTextValues,
            R.array.purpleTextValues,
            R.array.deepPurpleTextValues,

            R.array.indigoTextValues,
            R.array.blueTextValues,
            R.array.lightBlueTextValues,
            R.array.cyanTextValues,

            R.array.tealTextValues,
            R.array.greenTextValues,
            R.array.lightGreenTextValues,
            R.array.limeTextValues,

            R.array.yellowTextValues,
            R.array.amberTextValues,
            R.array.orangeTextValues,
            R.array.deepOrangeTextValues,

            R.array.brownTextValues,
            R.array.greyTextValues,
            R.array.blueGreyTextValues,

    };
    /**
     * English word array
     */
    private String[] englishWordArray;
    /**
     * Cached family name array
     */
    private String[] familyNameArray;
    /**
     * Cached female last name library
     */
    private String femaleNameTextLibrary;
    /**
     * Cached male last name library
     */
    private String maleNameLibrary;

    /**
     * some cached image url
     */
    private List<String> imageArray =null;

    private final Context context;

    public DataProvider(Context context) {
        this.context = context.getApplicationContext();
    }

    public String[] getWordList(){
        return getEnglishWordArray();
    }

    public List<String> getWordList(@IntRange(from = 0,to = 200) int count){
        return getWordList(0,count);
    }

    public List<String> getWordList(@IntRange(from = 0) int start, @IntRange(from = 0,to = 200) int count) {
        String[] items = new String[count];
        String[] englishWordArray = getEnglishWordArray();
        for (int i = 0; i < count; i++) {
            items[i] = englishWordArray[start+i];
        }
        return Arrays.asList(items);
    }

    public String getWord() {
        String[] englishWordArray = getEnglishWordArray();
        return englishWordArray[RANDOM.nextInt(englishWordArray.length)];
    }

    public String getName(){
        return RANDOM.nextBoolean()? getMaleName() : getFemaleName();
    }
    /**
     * Return a male name.
     * @return
     */
    public String getMaleName(){
        initFamilyNameData();
        int nameCount = 1+RANDOM.nextInt(1);
        Arrays.fill(nameTempArray,0,nameTempArray.length,' ');
        int familyNameIndex = RANDOM.nextInt(familyNameArray.length);
        String familyName=familyNameArray[familyNameIndex];
        for(int i=0;i<nameCount;i++){
            int index = RANDOM.nextInt(maleNameLibrary.length());
            nameTempArray[i]=maleNameLibrary.charAt(index);
        }
        return familyName+new String(nameTempArray).trim();
    }

    /**
     * Return a female name
     * @return
     */
    public String getFemaleName(){
        initFamilyNameData();
        int nameCount = 1+RANDOM.nextInt(1);
        Arrays.fill(nameTempArray,0,nameTempArray.length,' ');
        int familyNameIndex = RANDOM.nextInt(familyNameArray.length);
        String familyName=familyNameArray[familyNameIndex];
        for(int i=0;i<nameCount;i++){
            int index = RANDOM.nextInt(familyName.length());
            nameTempArray[i]=familyName.charAt(index);
        }
        return familyName+new String(nameTempArray).trim();
    }

    /**
     * Get a random color
     * @return
     */
    public static int getColor(){
        float red = RANDOM.nextInt(255);
        float green = RANDOM.nextInt(255) / 2f;
        float blue = RANDOM.nextInt(255) / 2f;
        return 0xff000000 | ((int) (red   * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) <<  8) | (int) (blue  * 255.0f + 0.5f);
    }

    /**
     * Get a specific color array
     * @param index
     * @return
     */
    public int[] getColorArray(@IntRange(from=COLOR_RED,to=COLOR_BLUE_GREY) int index){
        int[] cacheArray = colorSparseArray.get(index);
        if(null!=cacheArray){
            return cacheArray;
        } else {
            Resources resources = context.getResources();
            String[] stringArray = resources.getStringArray(COLOR_ATTR_ARRAY[index]);
            int[] colorArray=new int[stringArray.length];
            for(int i=0;i<colorArray.length;i++){
                colorArray[i]= Color.parseColor(stringArray[i]);
            }
            colorSparseArray.put(index,colorArray);
            return colorArray;
        }
    }

    /**
     * lazily load family name data from assets
     */
    private String[] getEnglishWordArray() {
        if(null!= englishWordArray){
            return englishWordArray;
        } else {
            final List<String> wordList=new ArrayList<>();
            AssetManager assets = context.getAssets();
            BufferedReader reader=null;
            try {
                InputStream inputStream = assets.open("data/english_word.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    wordList.add(line);
                }
                englishWordArray =wordList.toArray(new String[wordList.size()]);
            } catch (IOException e) {
                //nothing to do
            } finally {
                if(null!=reader){
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }
            return englishWordArray;
        }
    }

    /**
     * lazily load family name data from assets
     */
    private void initFamilyNameData() {
        if(null==familyNameArray) {
            AssetManager assets = context.getAssets();
            BufferedReader reader = null;
            try {
                InputStream inputStream = assets.open("data/family_name.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line=reader.readLine();
                familyNameArray=line.split(",");
            } catch (IOException e) {
            } finally {
                if (null != reader) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        if(null==femaleNameTextLibrary||null==maleNameLibrary){
            AssetManager assets = context.getAssets();
            InputStreamReader inputStreamReader=null;
            try {
                InputStream inputStream = assets.open("data/family_last_name.properties");
                Properties properties=new Properties();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                properties.load(inputStreamReader);
                maleNameLibrary=properties.getProperty("male");
                femaleNameTextLibrary=properties.getProperty("female");
            } catch (IOException e) {
            } finally {
                if (null != inputStreamReader) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    /**
     * Get test image array
     * @return
     */
    public List<String> getImageArray(){
        if(null!= imageArray){
            return imageArray;
        } else {
            final List<String> imageList=new ArrayList<>();
            AssetManager assets = context.getAssets();
            BufferedReader reader=null;
            try {
                InputStream inputStream = assets.open("data/image.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    imageList.add(line);
                }
            } catch (IOException e) {
            } finally {
                imageArray =imageList;
                if(null!=reader){
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }
            return imageArray;
        }
    }
}