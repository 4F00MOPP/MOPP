/*See Tokenizer.java for notes*/

    
    /***************************REGEX TABLE*********************************
        -Regular expressions are used for matching lines to tokens
        -Aside from minor program tweaks, at this point most of the work is just
        going to be adding the expressions for all three languages.
        -These need to be tested thoroughly to ensure they're working exactly
        how we intend them to (it's easy to make a regex that accepts what we
        want but also additional strings).
        -Add comment after each one to give insight to what is being matched
        -With how it currently checks, moving an expression up in the list
        increases its priority for being checked first
        */

package tokenizer;

public class Tokens {
    
    public static String [][] importTokens(){
        
        String [][] regExTable = new String[][]{
       
        {":"                                                    , ":COLON:"}, //GETS RID OF COLONS SO TOKENS WORK PROPERLY
        {"public static void main\\s*\\(\\s*String.*"           , ":MAIN:"}, //main method 
        {"(:|\\s+)\\w+\\s*\\(.*\\)\\{"                          , ":METHODNAME:"}, //METHOD NAMES;
        
        //this sets up a format that can be searched for later when tokenizing identifers (2nd pass)
        {"(:|\\s*)\\w+\\s*="                                    , " UNKNOWNVAR ="}, //identifiers

        {"=\\w*s*(\\[\\w*\\]\\s*)+"                             , " :ARRAY-ASSIGN:"}, //assigning values to arrays =[value]+
       
        // of the form: int aNumber = 100;
        {"\\bint\\b\\s+\\w+\\s*=\\s*[0-9]+\\s*;"                , ":NUM-VAR-DECLARATION::NUM-VAR::EQUAL::NUM-LITERAL::SEMICOLON:"},//int name = value;
        {"\\bbyte\\b\\s+\\w+\\s*=\\s*[0-9]*\\s*;"               , ":BYTE-VAR-DECLARATION::NUM-VAR::EQUAL:NUM-LITERAL::SEMICOLON:"},//byte name = value;
        {"\\bshort\\b\\s+\\w+\\s*=\\s*[0-9]*\\s*;"              , ":SHORT-VAR-DECLARATION::NUM-VAR::EQUAL:NUM-LITERAL::SEMICOLON:"},//short name = value;
        {"\\blong\\b\\s+\\w+\\s*=\\s*[0-9]*\\s*;"               , ":LONG-VAR-DECLARATION::NUM-VAR::EQUAL:NUM-LITERAL::SEMICOLON:"},//long name = value;
        {"\\bdouble\\b\\s+\\w+\\s*=\\s*[0-9]+[.]*[0-9]*\\s*;"   , ":DOUBLE-VAR-DECLARATION::NUM-VAR::EQUAL::NUM-LITERAL::SEMICOLON:"},//double name = value;
        {"\\bfloat\\b\\s+\\w+\\s*=\\s*[0-9]+[.]*[0-9]*\\s*;"    , ":FLOAT-VAR-DECLARATION::NUM-VAR::EQUAL::NUM-LITERAL::SEMICOLON:"},//float name = value;
        {"\\bString\\b\\s+\\w+\\s*=\\s*\"[A-Za-z0-9]*\"\\s*;"   , ":STRING-VAR-DECLARATION::STRING-VAR::EQUAL::STRING-LITERAL::SEMICOLON:"},//String name = value;
        {"\\bchar\\b\\s+\\w+\\s*=\\s*\'[A-Za-z0-9]\'\\s*;"      , ":CHAR-VAR-DECLARATION::STRING-VAR::EQUAL::STRING-LITERAL::SEMICOLON:"},//char name = value;
        {"\\bboolean\\b\\s+\\w+\\s*=\\s*(false|true)\\s*;"      , ":BOOLEAN-VAR-DECLARATION::BOOL-VAR::EQUAL::BOOL-LITERAL::SEMICOLON:"},//boolean name = value;
         
        
        //2D arrays of the form: int [][] aNumber;
        {"(^|\\s+)\\bint\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"          , ":NUM-ARRAY-DECLARATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bbyte\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"         , ":NUM-ARRAY-DECLARATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bshort\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"        , ":NUM-ARRAY-DECLARATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\blong\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"         , ":NUM-ARRAY-DECLARATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bdouble\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"       , ":NUM-VAR-DECLARATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bfloat\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"        , ":NUM-ARRAY-DECLARATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bString\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"       , ":STRING-ARRAY-DECLARATION::STRING-ARRAY::SEMICOLON:"},//String dec;
        {"(^|\\s+)\\bchar\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"         , ":CHAR-ARRAY-DECLARATION::STRING-ARRAY::SEMICOLON:"},//string dec;
        {"(^|\\s+)\\bboolean\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"      , ":BOOLEAN-ARRAY-DECLARATION::BOOL-ARRAY::SEMICOLON:"},//boolean dec;
        
        
        // of the form: int aNumber;
        {"(^|\\s+)\\bint\\b\\s+\\w+;"                           , ":NUM-VAR-DECLARATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bbyte\\b\\s+\\w+;"                          , ":NUM-VAR-DECLARATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bshort\\b\\s+\\w+;"                         , ":NUM-VAR-DECLARATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\blong\\b\\s+\\w+;"                          , ":NUM-VAR-DECLARATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bdouble\\b\\s+\\w+;"                        , ":NUM-VAR-DECLARATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bfloat\\b\\s+\\w+;"                         , ":NUM-VAR-DECLARATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bString\\b\\s+\\w+;"                        , ":STRING-VAR-DECLARATION::STRING-VAR::SEMICOLON:"},//String dec;
        {"(^|\\s+)\\bchar\\b\\s+\\w+;"                          , ":CHAR-VAR-DECLARATION::STRING-VAR::SEMICOLON:"},//string dec;
        {"(^|\\s+)\\bboolean\\b\\s+\\w+;"                       , ":BOOLEAN-VAR-DECLARATION::BOOL-VAR::SEMICOLON:"},//boolean dec;
        
        // of the form: int
        {"(^|\\s+)\\bint\\b\\s+"                                , ":NUM-VAR-DECLARATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bbyte\\b\\s+"                               , ":NUM-VAR-DECLARATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bshort\\b\\s+"                              , ":NUM-VAR-DECLARATION:"}, //numeral type declarations;
        {"(^|\\s+)\\blong\\b\\s+"                               , ":NUM-VAR-DECLARATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bdouble\\b\\s+"                             , ":NUM-VAR-DECLARATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bfloat\\b\\s+"                              , ":NUM-VAR-DECLARATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bString\\b\\s+"                             , ":STRING-VAR-DECLARATION:"},//String dec;
        {"(^|\\s+)\\bchar\\b\\s+"                               , ":CHAR-VAR-DECLARATION:"},//string dec;
        {"(^|\\s+)\\bboolean\\b\\s+"                            , ":BOOLEAN-VAR-DECLARATION:"},//boolean dec;
         
        //This finds any remaining identifers (i.e. from Objects)
        {"^.+\\s*="                                             , ":UNKNOWNVAR:="}, //identifier;
         
        {"[\\s+|\\{|=|(]\\d+(?:\\.\\d+)?"                       , ":NUM-LITERAL:"}, //any number by itself   
         
        //LOOPS
        {"if\\s*\\(.*"                                          , ":IF-LOOP:"},
        {"for\\s*\\(.*"                                         , ":FOR-LOOP:"}, 
        {"while\\s*\\(.*"                                       , ":WHILE-LOOP:"},
        
        {"/.*"                                                  ,":COMMENT:"},// Comments       
        {"^\\s*\\*.*"                                           ,":COMMENT:"},// Multi-line Comments       
        {".*System.*;"                                          , ":JAVAIO:"}, //anything to do with JAVA IO, this can be expanded
        {"enum\\s+\\w+.*"                                       , ":ENUM-DECLARATION:"}, 
        {"package\\s+.*"                                        , ":PACKAGE-DECLARATION:"},
        {"\".*\""                                               , ":STRING-OF-TEXT:"},   
        {"\'.*\'"                                               , ":STRING-OF-TEXT:"},   
        {"^\\s*$"                                               , ":EMPTY:"}, //EMPTY LINES
        {"\\s+abstract\\s+"                                     , ":ABSTRACT:"}, //RESERVED KEYWORDS
        {"\\s+continue\\s+"                                     , ":CONTINUE:"}, //RESERVED KEYWORDS
        {"\\s+for\\s+"                                          , ":FOR:"}, //RESERVED KEYWORDS
        {"\\s+new\\s+"                                          , ":NEW:"}, //RESERVED KEYWORDS
        {"\\s+switch\\s+"                                       , ":SWITCH:"}, //RESERVED KEYWORDS
        {"\\s+assert\\s+"                                       , ":ASSERT"}, //RESERVED KEYWORDS
        {"\\s+default\\s+"                                      , ":DEFAULT:"}, //RESERVED KEYWORDS
        {"\\s+goto\\s+"                                         , ":GOTO:"}, //RESERVED KEYWORDS
        {"\\s+package\\s+"                                      , ":PACKAGE:"}, //RESERVED KEYWORDS
        {"\\s+synchronized\\s+"                                 , ":SYNCHRONIZED:"}, //RESERVED KEYWORDS
        {"\\s+boolean\\s+"                                      , ":BOOLEAN:"}, //RESERVED KEYWORDS
        {"\\s+do\\s+"                                           , ":DO:"}, //RESERVED KEYWORDS
        {"\\s+if\\s+"                                           , ":IF:"}, //RESERVED KEYWORDS
        {"\\s+private\\s+"                                      , ":PRIVATE:"}, //RESERVED KEYWORDS
        {"\\s+this\\s+"                                         , ":THIS:"}, //RESERVED KEYWORDS
        {"\\s+byte\\s+"                                         , ":BYTE:"}, //RESERVED KEYWORDS
        {"\\s+else\\s+"                                         , ":ELSE:"}, //RESERVED KEYWORDS
        {"\\s+import\\s+"                                       , ":IMPORT:"}, //RESERVED KEYWORDS
        {"\\s+public\\s+"                                       , ":PUBLIC:"}, //RESERVED KEYWORDS
        {"\\s+throws\\s+"                                       , ":THROWS:"}, //RESERVED KEYWORDS
        {"\\s+case\\s+"                                         , ":CASE:"}, //RESERVED KEYWORDS
        {"\\s+enum\\s+"                                         , ":ENUM:"}, //RESERVED KEYWORDS
        {"\\s+instanceof\\s+"                                   , ":INSTANCEOF:"}, //RESERVED KEYWORDS
        {"\\s+return\\s+"                                       , ":RETURN:"}, //RESERVED KEYWORDS
        {"\\s+transient\\s+"                                    , ":TRANSIENT:"}, //RESERVED KEYWORDS
        {"\\s+catch\\s+"                                        , ":CATCH:"}, //RESERVED KEYWORDS
        {"\\s+extends\\s+"                                      , ":EXTENDS:"}, //RESERVED KEYWORDS
        {"\\s+int\\s+"                                          , ":INT:"}, //RESERVED KEYWORDS
        {"\\s+double\\s+"                                       , ":DOUBLE:"}, //RESERVED KEYWORDS
        {"\\s+short\\s+"                                        , ":SHORT:"}, //RESERVED KEYWORDS
        {"\\s+try\\s+"                                          , ":TRY:"}, //RESERVED KEYWORDS
        {"\\s+char\\s+"                                         , ":CHAR:"}, //RESERVED KEYWORDS
        {"\\s+final"                                            , ":FINAL:"}, //RESERVED KEYWORDS
        {"\\s+interface"                                        , ":INTERFACE:"}, //RESERVED KEYWORDS
        {"\\s+static"                                           , ":STATIC:"}, //RESERVED KEYWORDS
        {"^void|\\s+]void\\s+"                                  , ":VOID:"}, //RESERVED KEYWORDS
        {"\\s+class\\s+"                                        , ":CLASS:"}, //RESERVED KEYWORDS
        {"\\s+finally\\s+"                                      , ":FINALLY:"}, //RESERVED KEYWORDS
        {"\\s+long\\s+"                                         , ":LONG:"}, //RESERVED KEYWORDS
        {"\\s+strictfp\\s+"                                     , ":STRICTFP:"}, //RESERVED KEYWORDS
        {"\\s+volatile\\s+"                                     , ":VOLATILE:"}, //RESERVED KEYWORDS
        {"\\s+const\\s+"                                        , ":CONST:"}, //RESERVED KEYWORDS
        {"\\s+float\\s+"                                        , ":FLOAT:"}, //RESERVED KEYWORDS
        {"\\s+native\\s+"                                       , ":NATIVE:"}, //RESERVED KEYWORDS
        {"\\s+super\\s+"                                        , ":SUPER:"}, //RESERVED KEYWORDS
        {"\\}"                                                  , ":RBRACE:"}, //RESERVED KEYWORDS
        {"\\{"                                                  , ":LBRACE:"}, //RESERVED KEYWORDS
        {"\\)"                                                  , ":RBRACKET:"}, //RESERVED KEYWORDS
        {"\\("                                                  , ":LBRACKET:"}, //RESERVED KEYWORDS
        {"\\]"                                                  , ":RSQUAREBRACKET:"}, //RESERVED KEYWORDS
        {"\\["                                                  , ":LSQUAREBRACKET:"}, //RESERVED KEYWORDS
        {"\\="                                                  , ":EQUAL:"}, //RESERVED KEYWORDS
        {"\\-"                                                  , ":MINUS:"}, //RESERVED KEYWORDS
        {"\\*"                                                  , ":STAR:"}, //RESERVED KEYWORDS
        {"\\+"                                                  , ":PLUS:"}, //RESERVED KEYWORDS
        {"\\>"                                                  , ":GREATER:"}, //RESERVED KEYWORDS
        {"\\<"                                                  , ":LESS:"}, //RESERVED KEYWORDS
        {"\\%"                                                  , ":MOD:"}, //RESERVED KEYWORDS
        {"\\@"                                                  , ":AND:"}, //RESERVED KEYWORDS
        {"\\|"                                                  , ":OR:"}, //RESERVED KEYWORDS
        {"\\^"                                                  , ":XOR:"}, //RESERVED KEYWORDS
        {"\\~"                                                  , ":COMPLIMENT:"}, //RESERVED KEYWORDS
        {"\\,"                                                  , ":COMMA:"}, //RESERVED KEYWORDS
        {"\\!"                                                  , ":NOT:"}, //RESERVED KEYWORDS
        {"\\;"                                                  , ":SEMICOLON:"}, //RESERVED KEYWORDS
        
        //C and C++ tokens
        {"\\auto"                                               , ":AUTO:"}, //RESERVED KEYWORDS
        {"\\extern"                                             , ":EXTERN:"}, //RESERVED KEYWORDS
        {"\\register"                                           , ":REGISTER:"}, //RESERVED KEYWORDS
        {"\\signed"                                             , ":SIGNED:"}, //RESERVED KEYWORDS
        {"\\sizeof"                                             , ":SIZEOF:"}, //RESERVED KEYWORDS
        {"\\struct"                                             , ":STRUCT:"}, //RESERVED KEYWORDS
        {"\\typedef"                                            , ":TYPEDEF:"}, //RESERVED KEYWORDS
        {"\\asm"                                                , ":ASM:"}, //RESERVED KEYWORDS
        {"\\bool"                                               , ":BOOL:"}, //RESERVED KEYWORDS
        {"\\const_cast"                                         , ":CONST-CAST:"}, //RESERVED KEYWORDS
        {"\\delete"                                             , ":DELETE:"}, //RESERVED KEYWORDS
        {"\\dynamic_cast"                                       , ":DYNAMIC-CAST:"}, //RESERVED KEYWORDS
        {"\\explicit"                                           , ":EXPLICIT:"}, //RESERVED KEYWORDS
        {"\\namespace"                                          , ":NAMESPACE:"}, //RESERVED KEYWORDS
        {"\\typeid"                                             , ":TYPEID:"}, //RESERVED KEYWORDS
        {"\\wchar_t"                                            , ":WCHAR-T:"}, //RESERVED KEYWORDS
        //***************ADD ADDITIONAL REGEX HERE
        };
        
        return regExTable;   
    }
}