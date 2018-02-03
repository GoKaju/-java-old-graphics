/*     */ package com.statics.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcesaCaracEspeciales
/*     */ {
/*     */   public static String quitarEspeciales(String str, int a)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  16 */     String res = "";
/*     */     
/*  18 */     if (str != null)
/*     */     {
/*     */ 
/*  21 */       res = res.replaceAll("&aacute;", "a");
/*  22 */       res = res.replaceAll("&eacute;", "e");
/*  23 */       res = res.replaceAll("&iacute;", "i");
/*  24 */       res = res.replaceAll("&oacute;", "o");
/*  25 */       res = res.replaceAll("&uacute;", "u");
/*  26 */       res = res.replaceAll("&Aacute;", "A");
/*  27 */       res = res.replaceAll("&Eacute;", "E");
/*  28 */       res = res.replaceAll("&Iacute;", "I");
/*  29 */       res = res.replaceAll("&Oacute;", "O");
/*  30 */       res = res.replaceAll("&Uacute;", "U");
/*     */       
/*  32 */       res = res.replaceAll("&auml;", "a");
/*  33 */       res = res.replaceAll("&euml;", "e");
/*  34 */       res = res.replaceAll("&iuml;", "i");
/*  35 */       res = res.replaceAll("&ouml;", "o");
/*  36 */       res = res.replaceAll("&uuml;", "u");
/*  37 */       res = res.replaceAll("&Auml;", "A");
/*  38 */       res = res.replaceAll("&Euml;", "E");
/*  39 */       res = res.replaceAll("&Iuml;", "I");
/*  40 */       res = res.replaceAll("&Ouml;", "O");
/*  41 */       res = res.replaceAll("&Uuml;", "U");
/*     */       
/*  43 */       res = res.replaceAll("&Ntilde;", "N");
/*  44 */       res = res.replaceAll("&ntilde;", "n");
/*  45 */       res = res.replaceAll("&deg;", "_");
/*     */       
/*  47 */       res = res.replaceAll("&iquest;", "_");
/*  48 */       res = res.replaceAll("&iexcl;", "_");
/*     */       
/*  50 */       res = res.replaceAll("&acirc;", "a");
/*  51 */       res = res.replaceAll("&ecirc;", "e");
/*  52 */       res = res.replaceAll("&icirc;", "i");
/*  53 */       res = res.replaceAll("&ocirc;", "o");
/*  54 */       res = res.replaceAll("&ucirc;", "u");
/*     */       
/*  56 */       res = res.replaceAll("&Acirc;", "A");
/*  57 */       res = res.replaceAll("&Ecirc;", "E");
/*  58 */       res = res.replaceAll("&Icirc;", "I");
/*  59 */       res = res.replaceAll("&Ocirc;", "O");
/*  60 */       res = res.replaceAll("&Ucirc;", "U");
/*     */       
/*  62 */       res = res.replaceAll("&agrave;", "a");
/*  63 */       res = res.replaceAll("&egrave;", "e");
/*  64 */       res = res.replaceAll("&igrave;", "i");
/*  65 */       res = res.replaceAll("&ograve;", "o");
/*  66 */       res = res.replaceAll("&ugrave;", "u");
/*     */       
/*  68 */       res = res.replaceAll("&Agrave;", "A");
/*  69 */       res = res.replaceAll("&Egrave;", "E");
/*  70 */       res = res.replaceAll("&Igrave;", "I");
/*  71 */       res = res.replaceAll("&Ograve;", "O");
/*  72 */       res = res.replaceAll("&Ugrave;", "U");
/*     */       
/*  74 */       res = res.replaceAll("&ccedil;", "c");
/*  75 */       res = res.replaceAll("&Ccedil;", "C");
/*     */       
/*  77 */       res = res.replaceAll("á", "a");
/*     */       
/*  79 */       res = res.replaceAll("á", "a");
/*  80 */       res = res.replaceAll("é", "e");
/*  81 */       res = res.replaceAll("í", "i");
/*  82 */       res = res.replaceAll("ó", "o");
/*  83 */       res = res.replaceAll("ú", "u");
/*  84 */       res = res.replaceAll("Á", "A");
/*  85 */       res = res.replaceAll("É", "E");
/*  86 */       res = res.replaceAll("Í", "I");
/*  87 */       res = res.replaceAll("Ó", "O");
/*  88 */       res = res.replaceAll("Ú", "U");
/*     */       
/*  90 */       res = res.replaceAll("ä", "a");
/*  91 */       res = res.replaceAll("ë", "e");
/*  92 */       res = res.replaceAll("ï", "i");
/*  93 */       res = res.replaceAll("ö", "o");
/*  94 */       res = res.replaceAll("ü", "u");
/*  95 */       res = res.replaceAll("Ä", "A");
/*  96 */       res = res.replaceAll("Ë", "E");
/*  97 */       res = res.replaceAll("Ï", "I");
/*  98 */       res = res.replaceAll("Ö", "O");
/*  99 */       res = res.replaceAll("Ü", "U");
/*     */       
/* 101 */       res = res.replaceAll("Ñ", "N");
/* 102 */       res = res.replaceAll("ñ", "n");
/* 103 */       res = res.replaceAll("º", "_");
/*     */       
/* 105 */       res = res.replaceAll("¿", "_");
/* 106 */       res = res.replaceAll("¡", "_");
/*     */       
/* 108 */       res = res.replaceAll("â", "a");
/* 109 */       res = res.replaceAll("ê", "e");
/* 110 */       res = res.replaceAll("î", "i");
/* 111 */       res = res.replaceAll("ô", "o");
/* 112 */       res = res.replaceAll("û", "u");
/*     */       
/* 114 */       res = res.replaceAll("Â", "A");
/* 115 */       res = res.replaceAll("Ê", "E");
/* 116 */       res = res.replaceAll("Î", "I");
/* 117 */       res = res.replaceAll("Ô", "O");
/* 118 */       res = res.replaceAll("Û", "U");
/*     */       
/* 120 */       res = res.replaceAll("à", "a");
/* 121 */       res = res.replaceAll("è", "e");
/* 122 */       res = res.replaceAll("ì", "i");
/* 123 */       res = res.replaceAll("ò", "o");
/* 124 */       res = res.replaceAll("ù", "u");
/*     */       
/* 126 */       res = res.replaceAll("À", "A");
/* 127 */       res = res.replaceAll("È", "E");
/* 128 */       res = res.replaceAll("Ì", "I");
/* 129 */       res = res.replaceAll("Ò", "O");
/* 130 */       res = res.replaceAll("Ù", "U");
/*     */       
/* 132 */       res = res.replaceAll("ç", "c");
/* 133 */       res = res.replaceAll("Ç", "C");
/* 134 */       res = new String(str.getBytes(), "UTF-8");
/*     */     }
/*     */     
/* 137 */     return res;
/*     */   }
/*     */   
/*     */   public static String quitarEspeciales(String input) throws UnsupportedEncodingException
/*     */   {
/* 142 */     String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇñÑ";
/*     */     
/* 144 */     String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcCnN";
/* 145 */     String output = input;
/* 146 */     for (int i = 0; i < original.length(); i++)
/*     */     {
/* 148 */       output = output.replace(original.charAt(i), ascii.charAt(i));
/*     */     }
/* 150 */     return output;
/*     */   }
/*     */ }


/* Location:              D:\javap\Function_Library.jar!\valeria\metodos\ProcesaCaracEspeciales.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */