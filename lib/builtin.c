void print(char *s) { printf("%s", s); }

void println(char *s) { printf("%s\n", s); }

void printInt(int x) { printf("%d", x); }

void printlnInt(int x) { printf("%d\n", x); }

char *getString() {
  char *s = malloc(1 << 8);
  scanf("%s", s);
  return s;
}

int getInt() {
  int x;
  scanf("%d", &x);
  return x;
}

char *toString(int x) {
  char *s = malloc(1 << 8);
  sprintf(s, "%d", x);
  return s;
}

char *__mx_substring(char *s, int l, int r) {
  char *t = malloc(r - l + 1);
  for (int i = l; i < r; i++) t[i - l] = s[i];
  t[r - l] = '\0';
  return t;
}

int __mx_parseInt(char *s) {
  int x;
  sscanf(s, "%d", &x);
  return x;
}

int __mx_ord(char *s, int x) { return s[x]; }

unsigned char __mx_strlt(char *s, char *t) {
  return strcmp(s, t) < 0;
}

unsigned char __mx_strle(char *s, char *t) {
  return strcmp(s, t) <= 0;
}

unsigned char __mx_strgt(char *s, char *t) {
  return strcmp(s, t) > 0;
}

unsigned char __mx_strge(char *s, char *t) {
  return strcmp(s, t) >= 0;
}

unsigned char __mx_streq(char *s, char *t) {
  return strcmp(s, t) == 0;
}

unsigned char __mx_strneq(char *s, char *t) {
  return strcmp(s, t) != 0;
}