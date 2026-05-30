package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var answerText by remember { mutableStateOf("") }
    
    // Clear answer text when correct answer triggers next riddle
    LaunchedEffect(state.currentRiddle?.id) {
        answerText = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tomos: ${state.progress.score}", color = MaterialTheme.colorScheme.primary, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                actions = {
                    Text(
                        text = "Nível: ${state.progress.currentLevel}",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(end = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            
            if (state.progress.isFinished) {
                // Finale screen
                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "O ENIGMA FINAL FOI DESVENDADO",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Sua sabedoria final: ${state.progress.score} Tomos",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor)
                    ) {
                        Text("Retornar à Superfície", color = MaterialTheme.colorScheme.primary, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
                    }
                }
            } else {
                state.currentRiddle?.let { riddle ->
                    AnimatedContent(
                        targetState = riddle,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(800)) + slideInVertically(animationSpec = tween(800), initialOffsetY = { it }) togetherWith
                            fadeOut(animationSpec = tween(800)) + slideOutVertically(animationSpec = tween(800), targetOffsetY = { -it })
                        }, label = "RiddleTransition"
                    ) { targetRiddle ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Header Row flanking the Card with Book (left) and Scroll (right)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                androidx.compose.foundation.Image(
                                    painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_book_icon_1780098789806),
                                    contentDescription = "Livro do Conhecimento",
                                    modifier = Modifier
                                        .size(64.dp),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                                )
                                
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "ORÁCULO DAS SOMBRAS",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 2.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                        )
                                    )
                                    Text(
                                        text = "Desvende as Palavras Antigas",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = com.example.ui.theme.TextMuted,
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                        )
                                    )
                                }
                                
                                androidx.compose.foundation.Image(
                                    painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.img_scroll_icon_1780098806250),
                                    contentDescription = "Pergaminho Sagrado",
                                    modifier = Modifier
                                        .size(64.dp),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                                )
                            }
                            
                            // Question Card
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                shape = androidx.compose.foundation.shape.CutCornerShape(16.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor),
                                modifier = Modifier.fillMaxWidth().weight(1f)
                            ) {
                                Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("⚜", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp, modifier = Modifier.padding(bottom = 12.dp))
                                        Text(
                                            text = targetRiddle.question,
                                            style = MaterialTheme.typography.headlineMedium.copy(lineHeight = 36.sp),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            textAlign = TextAlign.Center,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                        )
                                        Text("⚜", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp, modifier = Modifier.padding(top = 12.dp))
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))

                            // Hint area
                            if (state.showHint) {
                                Text(
                                    text = "✦ Sussurro: ${targetRiddle.hint} ✦",
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    textAlign = TextAlign.Center,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                )
                            }
                            
                            if (state.isAlmostThere && !state.isCorrect) {
                                Text(
                                    text = "O selo vibra... a resposta está próxima.",
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                )
                            }
                            
                            if (state.isCorrect) {
                                Text(
                                    text = "A passagem se abre...",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                )
                            }
                            
                            // Answer Input
                            OutlinedTextField(
                                value = answerText,
                                onValueChange = { answerText = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = androidx.compose.foundation.shape.CutCornerShape(4.dp),
                                placeholder = { Text("Inscreva as palavras do pergaminho...", fontFamily = androidx.compose.ui.text.font.FontFamily.Serif) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(onDone = {
                                    viewModel.submitAnswer(answerText)
                                }),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedButton(
                                    onClick = { viewModel.revealHint() },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.BorderColor),
                                    shape = androidx.compose.foundation.shape.CutCornerShape(8.dp),
                                    modifier = Modifier.weight(1f).padding(end = 8.dp).height(56.dp),
                                    enabled = !state.showHint && targetRiddle.id != 50
                                ) {
                                    Text("🕯️ Invocar Luz (-5)", fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
                                }
                                
                                Button(
                                    onClick = { viewModel.submitAnswer(answerText) },
                                    colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
                                    contentPadding = PaddingValues(),
                                    modifier = Modifier.weight(1f).padding(start = 8.dp).height(56.dp)
                                        .background(
                                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                                listOf(com.example.ui.theme.BloodRed, com.example.ui.theme.DarkRedGradient)
                                            ),
                                            shape = androidx.compose.foundation.shape.CutCornerShape(8.dp)
                                        )
                                ) {
                                    Text("DESVENDAR O SELO", color = androidx.compose.ui.graphics.Color.White, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(48.dp))
                        }
                    }
                }
            }
        }
    }
}
