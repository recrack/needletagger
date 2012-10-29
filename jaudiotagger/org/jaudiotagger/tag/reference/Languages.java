/**
 * @author : Paul Taylor
 * <p/>
 * Version @version:$Id: Languages.java 921 2010-10-14 11:04:46Z paultaylor $
 * <p/>
 * Jaudiotagger Copyright (C)2004,2005
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 * you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * <p/>
 * Description:
 *  Valid Languages for ID Tags
 */
package org.jaudiotagger.tag.reference;

import org.jaudiotagger.tag.datatype.AbstractStringStringValuePair;

public class Languages extends AbstractStringStringValuePair
{
    public static final String DEFAULT_ID = "eng";
    public static final String MEDIA_MONKEY_ID = "XXX";
    public static final String WINAMP_ID = "\0\0\0";

    public static final String DEFAULT_VALUE = "English";

    //The number of bytes used to hold the language field size
    public static final int LANGUAGE_FIELD_SIZE = 3;

    private static Languages languageTypes;


    public static Languages getInstanceOf()
    {
        if (languageTypes == null)
        {
            languageTypes = new Languages();
        }
        return languageTypes;
    }

    private Languages()
    {
        idToValue.put("aar", "Afar");
        idToValue.put("abk", "Abkhazian");
        idToValue.put("ace", "Achinese");
        idToValue.put("ach", "Acoli");
        idToValue.put("ada", "Adangme");
        idToValue.put("afa", "Afro-Asiatic (Other)");
        idToValue.put("afh", "Afrihili");
        idToValue.put("afr", "Afrikaans");
        idToValue.put("aka", "Akan");
        idToValue.put("akk", "Akkadian");
        idToValue.put("alb", "Albanian");
        idToValue.put("ale", "Aleut");
        idToValue.put("alg", "Algonquian languages");
        idToValue.put("amh", "Amharic");
        idToValue.put("ang", "English, Old (ca.450-1100)");
        idToValue.put("apa", "Apache languages");
        idToValue.put("ara", "Arabic");
        idToValue.put("arc", "Aramaic");
        idToValue.put("arm", "Armenian");
        idToValue.put("arn", "Araucanian");
        idToValue.put("arp", "Arapaho");
        idToValue.put("art", "Artificial (Other)");
        idToValue.put("arw", "Arawak");
        idToValue.put("asm", "Assamese");
        idToValue.put("ast", "Asturian; Bable");
        idToValue.put("ath", "Athapascan languages");
        idToValue.put("aus", "Australian languages");
        idToValue.put("ava", "Avaric");
        idToValue.put("ave", "Avestan");
        idToValue.put("awa", "Awadhi");
        idToValue.put("aym", "Aymara");
        idToValue.put("aze", "Azerbaijani");
        idToValue.put("bad", "Banda");
        idToValue.put("bai", "Bamileke languages");
        idToValue.put("bak", "Bashkir");
        idToValue.put("bal", "Baluchi");
        idToValue.put("bam", "Bambara");
        idToValue.put("ban", "Balinese");
        idToValue.put("baq", "Basque");
        idToValue.put("bas", "Basa");
        idToValue.put("bat", "Baltic (Other)");
        idToValue.put("bej", "Beja");
        idToValue.put("bel", "Belarusian");
        idToValue.put("bem", "Bemba");
        idToValue.put("ben", "Bengali");
        idToValue.put("ber", "Berber (Other)");
        idToValue.put("bho", "Bhojpuri");
        idToValue.put("bih", "Bihari");
        idToValue.put("bik", "Bikol");
        idToValue.put("bin", "Bini");
        idToValue.put("bis", "Bislama");
        idToValue.put("bla", "Siksika");
        idToValue.put("bnt", "Bantu (Other)");
        idToValue.put("bod", "Tibetan");
        idToValue.put("bos", "Bosnian");
        idToValue.put("bra", "Braj");
        idToValue.put("bre", "Breton");
        idToValue.put("btk", "Batak (Indonesia)");
        idToValue.put("bua", "Buriat");
        idToValue.put("bug", "Buginese");
        idToValue.put("bul", "Bulgarian");
        idToValue.put("bur", "Burmese");
        idToValue.put("cad", "Caddo");
        idToValue.put("cai", "Central American Indian (Other)");
        idToValue.put("car", "Carib");
        idToValue.put("cat", "Catalan");
        idToValue.put("cau", "Caucasian (Other)");
        idToValue.put("ceb", "Cebuano");
        idToValue.put("cel", "Celtic (Other)");
        idToValue.put("ces", "Czech");
        idToValue.put("cha", "Chamorro");
        idToValue.put("chb", "Chibcha");
        idToValue.put("che", "Chechen");
        idToValue.put("chg", "Chagatai");
        idToValue.put("chi", "Chinese");
        idToValue.put("chk", "Chuukese");
        idToValue.put("chm", "Mari");
        idToValue.put("chn", "Chinook jargon");
        idToValue.put("cho", "Choctaw");
        idToValue.put("chp", "Chipewyan");
        idToValue.put("chr", "Cherokee");
        idToValue.put("chu", "Church Slavic; Old Slavonic; Old Church Slavonic; Church Slavonic; Old Bulgarian");
        idToValue.put("chv", "Chuvash");
        idToValue.put("chy", "Cheyenne");
        idToValue.put("cmc", "Chamic languages");
        idToValue.put("cop", "Coptic");
        idToValue.put("cor", "Cornish");
        idToValue.put("cos", "Corsican");
        idToValue.put("cpe", "Creoles and pidgins, English based (Other)");
        idToValue.put("cpf", "Creoles and pidgins, French-based (Other)");
        idToValue.put("cpp", "Creoles and pidgins,");
        idToValue.put("cre", "Cree");
        idToValue.put("crp", "Creoles and pidgins (Other)");
        idToValue.put("cus", "Cushitic (Other)");
        idToValue.put("cym", "Welsh");
        idToValue.put("cze", "Czech");
        idToValue.put("dak", "Dakota");
        idToValue.put("dan", "Danish");
        idToValue.put("day", "Dayak");
        idToValue.put("del", "Delaware");
        idToValue.put("den", "Slave (Athapascan)");
        idToValue.put("deu", "German");
        idToValue.put("dgr", "Dogrib");
        idToValue.put("din", "Dinka");
        idToValue.put("div", "Divehi");
        idToValue.put("doi", "Dogri");
        idToValue.put("dra", "Dravidian (Other)");
        idToValue.put("dua", "Duala");
        idToValue.put("dum", "Dutch, Middle (ca.1050-1350)");
        idToValue.put("dut", "Dutch");
        idToValue.put("dyu", "Dyula");
        idToValue.put("dzo", "Dzongkha");
        idToValue.put("efi", "Efik");
        idToValue.put("egy", "Egyptian (Ancient)");
        idToValue.put("eka", "Ekajuk");
        idToValue.put("ell", "Greek, Modern (1453-)");
        idToValue.put("elx", "Elamite");
        idToValue.put("eng", "English");
        idToValue.put("enm", "English, Middle (1100-1500)");
        idToValue.put("epo", "Esperanto");
        idToValue.put("est", "Estonian");
        idToValue.put("eus", "Basque");
        idToValue.put("ewe", "Ewe");
        idToValue.put("ewo", "Ewondo");
        idToValue.put("fan", "Fang");
        idToValue.put("fao", "Faroese");
        idToValue.put("fas", "Persian");
        idToValue.put("fat", "Fanti");
        idToValue.put("fij", "Fijian");
        idToValue.put("fin", "Finnish");
        idToValue.put("fiu", "Finno-Ugrian (Other)");
        idToValue.put("fon", "Fon");
        idToValue.put("fra", "French");
        idToValue.put("frm", "French, Middle (ca.1400-1800)");
        idToValue.put("fro", "French, Old (842-ca.1400)");
        idToValue.put("fry", "Frisian");
        idToValue.put("ful", "Fulah");
        idToValue.put("fur", "Friulian");
        idToValue.put("gaa", "Ga");
        idToValue.put("gay", "Gayo");
        idToValue.put("gba", "Gbaya");
        idToValue.put("gem", "Germanic (Other)");
        idToValue.put("geo", "Georgian");
        idToValue.put("ger", "German");
        idToValue.put("gez", "Geez");
        idToValue.put("gil", "Gilbertese");
        idToValue.put("gla", "Gaelic; Scottish Gaelic");
        idToValue.put("gle", "Irish");
        idToValue.put("glg", "Gallegan");
        idToValue.put("glv", "Manx");
        idToValue.put("gmh", "German, Middle High (ca.1050-1500)");
        idToValue.put("goh", "German, Old High (ca.750-1050)");
        idToValue.put("gon", "Gondi");
        idToValue.put("gor", "Gorontalo");
        idToValue.put("got", "Gothic");
        idToValue.put("grb", "Grebo");
        idToValue.put("grc", "Greek, Ancient (to 1453)");
        idToValue.put("gre", "Greek, Modern (1453-)");
        idToValue.put("grn", "Guarani");
        idToValue.put("guj", "Gujarati");
        idToValue.put("gwi", "Gwich´in");
        idToValue.put("hai", "Haida");
        idToValue.put("hau", "Hausa");
        idToValue.put("haw", "Hawaiian");
        idToValue.put("heb", "Hebrew");
        idToValue.put("her", "Herero");
        idToValue.put("hil", "Hiligaynon");
        idToValue.put("him", "Himachali");
        idToValue.put("hin", "Hindi");
        idToValue.put("hit", "Hittite");
        idToValue.put("hmn", "Hmong");
        idToValue.put("hmo", "Hiri Motu");
        idToValue.put("hrv", "Croatian");
        idToValue.put("hun", "Hungarian");
        idToValue.put("hup", "Hupa");
        idToValue.put("hye", "Armenian");
        idToValue.put("iba", "Iban");
        idToValue.put("ibo", "Igbo");
        idToValue.put("ice", "Icelandic");
        idToValue.put("ido", "Ido");
        idToValue.put("ijo", "Ijo");
        idToValue.put("iku", "Inuktitut");
        idToValue.put("ile", "Interlingue");
        idToValue.put("ilo", "Iloko");
        idToValue.put("ina", "Interlingua (International Auxiliary)");
        idToValue.put("inc", "Indic (Other)");
        idToValue.put("ind", "Indonesian");
        idToValue.put("ine", "Indo-European (Other)");
        idToValue.put("ipk", "Inupiaq");
        idToValue.put("ira", "Iranian (Other)");
        idToValue.put("iro", "Iroquoian languages");
        idToValue.put("isl", "Icelandic");
        idToValue.put("ita", "Italian");
        idToValue.put("jav", "Javanese");
        idToValue.put("jpn", "Japanese");
        idToValue.put("jpr", "Judeo-Persian");
        idToValue.put("jrb", "Judeo-Arabic");
        idToValue.put("kaa", "Kara-Kalpak");
        idToValue.put("kab", "Kabyle");
        idToValue.put("kac", "Kachin");
        idToValue.put("kal", "Kalaallisut");
        idToValue.put("kam", "Kamba");
        idToValue.put("kan", "Kannada");
        idToValue.put("kar", "Karen");
        idToValue.put("kas", "Kashmiri");
        idToValue.put("kat", "Georgian");
        idToValue.put("kau", "Kanuri");
        idToValue.put("kaw", "Kawi");
        idToValue.put("kaz", "Kazakh");
        idToValue.put("kha", "Khasi");
        idToValue.put("khi", "Khoisan (Other)");
        idToValue.put("khm", "Khmer");
        idToValue.put("kho", "Khotanese");
        idToValue.put("kik", "Kikuyu; Gikuyu");
        idToValue.put("kin", "Kinyarwanda");
        idToValue.put("kir", "Kirghiz");
        idToValue.put("kmb", "Kimbundu");
        idToValue.put("kok", "Konkani");
        idToValue.put("kom", "Komi");
        idToValue.put("kon", "Kongo");
        idToValue.put("kor", "Korean");
        idToValue.put("kos", "Kosraean");
        idToValue.put("kpe", "Kpelle");
        idToValue.put("kro", "Kru");
        idToValue.put("kru", "Kurukh");
        idToValue.put("kua", "Kuanyama; Kwanyama");
        idToValue.put("kum", "Kumyk");
        idToValue.put("kur", "Kurdish");
        idToValue.put("kut", "Kutenai");
        idToValue.put("lad", "Ladino");
        idToValue.put("lah", "Lahnda");
        idToValue.put("lam", "Lamba");
        idToValue.put("lao", "Lao");
        idToValue.put("lat", "Latin");
        idToValue.put("lav", "Latvian");
        idToValue.put("lez", "Lezghian");
        idToValue.put("lin", "Lingala");
        idToValue.put("lit", "Lithuanian");
        idToValue.put("lol", "Mongo");
        idToValue.put("loz", "Lozi");
        idToValue.put("ltz", "Luxembourgish; Letzeburgesch");
        idToValue.put("lua", "Luba-Lulua");
        idToValue.put("lub", "Luba-Katanga");
        idToValue.put("lug", "Ganda");
        idToValue.put("lui", "Luiseno");
        idToValue.put("lun", "Lunda");
        idToValue.put("luo", "Luo (Kenya and Tanzania)");
        idToValue.put("lus", "lushai");
        idToValue.put("mac", "Macedonian");
        idToValue.put("mad", "Madurese");
        idToValue.put("mag", "Magahi");
        idToValue.put("mah", "Marshallese");
        idToValue.put("mai", "Maithili");
        idToValue.put("mak", "Makasar");
        idToValue.put("mal", "Malayalam");
        idToValue.put("man", "Mandingo");
        idToValue.put("mao", "Maori");
        idToValue.put("map", "Austronesian (Other)");
        idToValue.put("mar", "Marathi");
        idToValue.put("mas", "Masai");
        idToValue.put("may", "Malay");
        idToValue.put("mdr", "Mandar");
        idToValue.put("men", "Mende");
        idToValue.put("mga", "Irish, Middle (900-1200)");
        idToValue.put("mic", "Micmac");
        idToValue.put("min", "Minangkabau");
        idToValue.put("mis", "Miscellaneous languages");
        idToValue.put("mkd", "Macedonian");
        idToValue.put("mkh", "Mon-Khmer (Other)");
        idToValue.put("mlg", "Malagasy");
        idToValue.put("mlt", "Maltese");
        idToValue.put("mnc", "Manchu");
        idToValue.put("mni", "Manipuri");
        idToValue.put("mno", "Manobo languages");
        idToValue.put("moh", "Mohawk");
        idToValue.put("mol", "Moldavian");
        idToValue.put("mon", "Mongolian");
        idToValue.put("mos", "Mossi");
        idToValue.put("mri", "Maori");
        idToValue.put("msa", "Malay");
        idToValue.put("mul", "Multiple languages");
        idToValue.put("mun", "Munda languages");
        idToValue.put("mus", "Creek");
        idToValue.put("mwr", "Marwari");
        idToValue.put("mya", "Burmese");
        idToValue.put("myn", "Mayan languages");
        idToValue.put("nah", "Nahuatl");
        idToValue.put("nai", "North American Indian");
        idToValue.put("nau", "Nauru");
        idToValue.put("nav", "Navajo; Navaho");
        idToValue.put("nbl", "South Ndebele");
        idToValue.put("nde", "North Ndebele");
        idToValue.put("ndo", "Ndonga");
        idToValue.put("nds", "Low German; Low Saxon; German, Low; Saxon, Low");
        idToValue.put("nep", "Nepali");
        idToValue.put("new", "Newari");
        idToValue.put("nia", "Nias");
        idToValue.put("nic", "Niger-Kordofanian (Other)");
        idToValue.put("niu", "Niuean");
        idToValue.put("nld", "Dutch");
        idToValue.put("nno", "Norwegian Nynorsk");
        idToValue.put("nob", "Norwegian Bokmål");
        idToValue.put("non", "Norse, Old");
        idToValue.put("nor", "Norwegian");
        idToValue.put("nso", "Sotho, Northern");
        idToValue.put("nub", "Nubian languages");
        idToValue.put("nya", "Chichewa; Chewa; Nyanja");
        idToValue.put("nym", "Nyamwezi");
        idToValue.put("nyn", "Nyankole");
        idToValue.put("nyo", "Nyoro");
        idToValue.put("nzi", "Nzima");
        idToValue.put("oci", "Occitan (post 1500); Provençal");
        idToValue.put("oji", "Ojibwa");
        idToValue.put("ori", "Oriya");
        idToValue.put("orm", "Oromo");
        idToValue.put("osa", "Osage");
        idToValue.put("oss", "Ossetian; Ossetic");
        idToValue.put("ota", "Turkish, Ottoman (1500-1928)");
        idToValue.put("oto", "Otomian languages");
        idToValue.put("paa", "Papuan (Other)");
        idToValue.put("pag", "Pangasinan");
        idToValue.put("pal", "Pahlavi");
        idToValue.put("pam", "Pampanga");
        idToValue.put("pan", "Panjabi");
        idToValue.put("pap", "Papiamento");
        idToValue.put("pau", "Palauan");
        idToValue.put("peo", "Persian, Old (ca.600-400 B.C.)");
        idToValue.put("per", "Persian");
        idToValue.put("per", "Persian");
        idToValue.put("phi", "Philippine (Other)");
        idToValue.put("phn", "Phoenician");
        idToValue.put("pli", "Pali");
        idToValue.put("pol", "Polish");
        idToValue.put("pon", "Pohnpeian");
        idToValue.put("por", "Portuguese");
        idToValue.put("pra", "Prakrit languages");
        idToValue.put("pro", "Provençal, Old (to 1500)");
        idToValue.put("pus", "Pushto");
        idToValue.put("que", "Quechua");
        idToValue.put("raj", "Rajasthani");
        idToValue.put("rap", "Rapanui");
        idToValue.put("rar", "Rarotongan");
        idToValue.put("roa", "Romance (Other)");
        idToValue.put("roh", "Raeto-Romance");
        idToValue.put("rom", "Romany");
        idToValue.put("ron", "Romanian");
        idToValue.put("rum", "Romanian");
        idToValue.put("run", "Rundi");
        idToValue.put("rus", "Russian");
        idToValue.put("sad", "Sandawe");
        idToValue.put("sag", "Sango");
        idToValue.put("sah", "Yakut");
        idToValue.put("sai", "South American Indian (Other)");
        idToValue.put("sal", "Salishan languages");
        idToValue.put("sam", "Samaritan Aramaic");
        idToValue.put("san", "Sanskrit");
        idToValue.put("sas", "Sasak");
        idToValue.put("sat", "Santali");
        idToValue.put("scc", "Serbian");
        idToValue.put("sco", "Scots");
        idToValue.put("scr", "Croatian");
        idToValue.put("sel", "Selkup");
        idToValue.put("sem", "Semitic (Other)");
        idToValue.put("sga", "Irish, Old (to 900)");
        idToValue.put("sgn", "Sign languages");
        idToValue.put("shn", "Shan");
        idToValue.put("sid", "Sidamo");
        idToValue.put("sin", "Sinhales");
        idToValue.put("sio", "Siouan languages");
        idToValue.put("sit", "Sino-Tibetan (Other)");
        idToValue.put("sla", "Slavic (Other)");
        idToValue.put("slk", "Slovak");
        idToValue.put("slo", "Slovak");
        idToValue.put("slv", "Slovenian");
        idToValue.put("sma", "Southern Sami");
        idToValue.put("sme", "Northern Sami");
        idToValue.put("smi", "Sami languages (Other)");
        idToValue.put("smj", "Lule Sami");
        idToValue.put("smn", "Inari Sami");
        idToValue.put("smo", "Samoan");
        idToValue.put("sms", "Skolt Sami");
        idToValue.put("sna", "Shona");
        idToValue.put("snd", "Sindhi");
        idToValue.put("snk", "Soninke");
        idToValue.put("sog", "Sogdian");
        idToValue.put("som", "Somali");
        idToValue.put("son", "Songhai");
        idToValue.put("sot", "Sotho, Southern");
        idToValue.put("spa", "Spanish; Castilia");
        idToValue.put("sqi", "Albanian");
        idToValue.put("srd", "Sardinian");
        idToValue.put("srp", "Serbian");
        idToValue.put("srr", "Serer");
        idToValue.put("ssa", "Nilo-Saharan (Other)");
        idToValue.put("sus", "Susu");
        idToValue.put("sux", "Sumerian");
        idToValue.put("swa", "Swahili");
        idToValue.put("swe", "Swedish");
        idToValue.put("syr", "Syriac");
        idToValue.put("tah", "Tahitian");
        idToValue.put("tai", "Tai (Other)");
        idToValue.put("tam", "Tamil");
        idToValue.put("tat", "Tatar");
        idToValue.put("tel", "Telugu");
        idToValue.put("tem", "Timne");
        idToValue.put("ter", "Tereno");
        idToValue.put("tet", "Tetum");
        idToValue.put("tgk", "Tajik");
        idToValue.put("tgl", "Tagalog");
        idToValue.put("tha", "Thai");
        idToValue.put("tib", "Tibetan");
        idToValue.put("tig", "Tigre");
        idToValue.put("tir", "Tigrinya");
        idToValue.put("tiv", "Tiv");
        idToValue.put("tkl", "Tokelau");
        idToValue.put("tli", "Tlingit");
        idToValue.put("tmh", "Tamashek");
        idToValue.put("tog", "Tonga (Nyasa)");
        idToValue.put("ton", "Tonga (Tonga Islands)");
        idToValue.put("tpi", "Tok Pisin");
        idToValue.put("tsi", "Tsimshian");
        idToValue.put("tsn", "Tswana");
        idToValue.put("tso", "Tsonga");
        idToValue.put("tuk", "Turkmen");
        idToValue.put("tum", "Tumbuka");
        idToValue.put("tup", "Tupi languages");
        idToValue.put("tur", "Turkish");
        idToValue.put("tut", "Altaic (Other)");
        idToValue.put("tvl", "Tuvalu");
        idToValue.put("twi", "Twi");
        idToValue.put("tyv", "Tuvinian");
        idToValue.put("uga", "Ugaritic");
        idToValue.put("uig", "Uighur");
        idToValue.put("ukr", "Ukrainian");
        idToValue.put("umb", "Umbundu");
        idToValue.put("und", "Undetermined");
        idToValue.put("urd", "Urdu");
        idToValue.put("uzb", "Uzbek");
        idToValue.put("vai", "Vai");
        idToValue.put("ven", "Venda");
        idToValue.put("vie", "Vietnamese");
        idToValue.put("vol", "Volapük");
        idToValue.put("vot", "Votic");
        idToValue.put("wak", "Wakashan languages");
        idToValue.put("wal", "Walamo");
        idToValue.put("war", "Waray");
        idToValue.put("was", "Washo");
        idToValue.put("wel", "Welsh");
        idToValue.put("wen", "Sorbian languages");
        idToValue.put("wln", "Walloon");
        idToValue.put("wol", "Wolof");
        idToValue.put("xho", "Xhosa");
        idToValue.put("yao", "Yao");
        idToValue.put("yap", "Yapese");
        idToValue.put("yid", "Yiddish");
        idToValue.put("yor", "Yoruba");
        idToValue.put("ypk", "Yupik languages");
        idToValue.put("zap", "Zapotec");
        idToValue.put("zen", "Zenaga");
        idToValue.put("zha", "Zhuang; Chuang");
        idToValue.put("zho", "Chinese");
        idToValue.put("znd", "Zande");
        idToValue.put("zul", "Zulu");
        idToValue.put("zun", "Zuni");
        idToValue.put("\0\0\0", "Winamp Format");                 //Not Part of Spec but commonly used by some applications
        idToValue.put("XXX", "Media Monkey Format");              //Not Part of Spec but commonly used by some applications



        createMaps();
    }
}