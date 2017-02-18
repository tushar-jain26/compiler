
void   Open_File();
void   Demage_Lexeme();
int    Search(char[256],int);
void   analyze();
void   Skip_Comment();
void   Read_String();
void   Is_Keyword_Or_Not();
void   Is_Identifier_Or_Not();
void   Is_Operator_Or_Not();
void   Read_Number();
void   Is_Special_Or_Not();
void   Is_Comparison_Or_Not();
void   Add_To_Lexical (char[256],int,char[256]);
void   Print_ST();
void   Print_TOKEN();
void   Token_Attribute();



struct lexical
{
    char    data[256];          //Value of token.
    int     line[256];          //Line # which token appear in input 
file.
    int     times;              //# of times that token appear in input
file.
    char    type[256];           //Type of each token.
    struct  lexical *next;
};

typedef struct lexical Lex;
typedef Lex *lex;

/**********************************************************
File pointer for accessing the file.
***********************************************************/

FILE *fp;
FILE *st;
FILE *token;
char lexeme[256],ch;
int f,flag,line=1,i=1;
lex head=NULL,tail=NULL;

/***********************************************************
Array holding all keywords for checking.
***********************************************************/

char 
*keywords[]={"procedure","is","begin","end","var","cin","cout","if",
		  "then","else","and","or","not","loop","exit","when",
		  "while","until"};

/************************************************************
Array holding all arithmetic operations for checking.
************************************************************/

char arithmetic_operator[]={'+','-','*','/'};

/************************************************************
Array holding all comparison operations for checking.
************************************************************/

char *comparison_operator[]={"<",">","=","<=","<>",">="};

/************************************************************
Array holding all special for checking.
************************************************************/

char special[]={'%','!','@','~','$'};

/************************************************************

			**************
			*MAIN PROGRAM*
			**************

************************************************************/

void main()
{
  Open_File();
  analyze();
  fclose(fp);
  Print_ST();
  Print_TOKEN();
}

/***********************************************************
This function open input sourse file.
************************************************************/

void Open_File()
{

  fp=fopen("source.txt","r");   //provide path for source.txt here
  if(fp==NULL)
  {
	printf("!!!Can't open input file - source.txt!!!");
	getch();
	exit(0);
  }
}

/***********************************************************
Function to add item to structure of array to store data and
information of lexical items.
***********************************************************/

void Add_To_Lexical (char value[256],int line,char type[256])
{
	lex new_lex;

	if (!Search(value,line))    //When return 1 the token not found.
	{

	  new_lex=malloc(sizeof(Lex));

	  if (new_lex!=NULL)
	  {
		strcpy(new_lex->data,value);
		new_lex->line[0]=line;
		new_lex->times=1;
		strcpy(new_lex->type,type);
		new_lex->next=NULL;

		if (head==NULL)
		   head=new_lex;
		else
		   tail->next=new_lex;

		tail=new_lex;
	  }
	}
}

/***********************************************************
Function to search token.
***********************************************************/

int Search (char value[256],int line)
{
  lex x=head;
  int flag=0;

  while (x->next!=NULL && !flag)
  {
    if (strcmp(x->data,value)==0)
    {
      x->line[x->times]=line;
      x->times++;
      flag=1;
    }
    x=x->next;
  }
  return flag;
}

/************************************************************
Function to print the ST.TXT .
*************************************************************/

void Print_ST()
{
  lex x=head;
  int j;

  if ((st=fopen("ST.TXT","w"))==NULL)
      printf("The file ST.TXT cat not open. 
");

  else

  {
    System.out.println(st,"	 %s 	    %s 	 %s 
","Line#","Lexeme","Type");
    System.out.println(st,"	 ---- 	    ------ 	 ---- 
");

    while (x!=NULL)
    {
      if ((strcmp(x->type,"num")==0)         ||
	 (strcmp(x->type,"keyword")==0)      ||
	 (strcmp(x->type,"identifier")==0))
      {
	 System.out.println(st,"	 ");

	 for (j=0;j<x->times;j++)
	 {
	   System.out.println(st,"%d",x->line[j]);
		if (j!=x->times-1)      //This condition to prevent the comma
	   System.out.println(st,",",x->line[j]);  //"," to not print after last line #.
	 }

	System.out.println(st,"	    %-6s   	%-6s 
",x->data,x->type);
      }
      x=x->next;
    }

    fclose(st);
  }
}

/***********************************************************
Function to print the TOKENS.TXT .
***********************************************************/

void Print_TOKEN()
{
  int flag=0;

  fp=fopen("source.txt","r");

    if(fp==NULL)
    {
       printf("!!!Can't open input file - source.txt!!!");
       getch();
       exit(0);
    }

  else

    {
	if ((token=fopen("TOKENS.TXT","w"))==NULL)
	  printf("The file ST.TXT cat not open. 
");

      else

      {
	ch=fgetc(fp);

	while (!(feof(fp)))
	{

	  if (ch==' ' && !flag)
	  {
	    do
	      ch=fgetc(fp);
	    while (ch==' ');

	    fseek(fp,-2,1);
	    ch=fgetc(fp);
	    flag=1;
	  }

	  if (ch!='
' && ch!='	')
	    System.out.println(token,"%c",ch);

	  if (ch=='
')
	  {
	    System.out.println(token,"
");
	    Token_Attribute();
	    i++;
	    flag=0;
	  }

	  ch=fgetc(fp);
	}
      }
    }
    fclose(fp);
    fclose(token);
}







	
