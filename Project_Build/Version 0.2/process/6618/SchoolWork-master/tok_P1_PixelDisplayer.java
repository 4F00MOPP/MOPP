:LINE-1::importjava.awt.Color::SEMICOLON:
:LINE-2::importjava.awt.Dimension::SEMICOLON:
:LINE-3::importjava.awt.Graphics::SEMICOLON:
:LINE-4::importjava.awt.event.ActionEvent::SEMICOLON:
:LINE-5::importjava.awt.event.ActionListener::SEMICOLON:
:LINE-6::importjava.util.Random::SEMICOLON:
:LINE-7::EMPTY:
:LINE-8::importjavax.swing.JButton::SEMICOLON:
:LINE-9::importjavax.swing.JFrame::SEMICOLON:
:LINE-10::importjavax.swing.JPanel::SEMICOLON:
:LINE-11::EMPTY:
:LINE-12::public::CLASS::P1_PixelDisplayer::EXTENDS::JFrameimplementsActionListener::LBRACE:
:LINE-13::EMPTY:
:LINE-14::P1_PixelArray::UNKNOWNVAR::SEMICOLON:
:LINE-15::NUM::MINUS::VAR::MINUS::DECLATATION::NUM::MINUS::VAR::SEMICOLON:
:LINE-16::NUM::MINUS::VAR::MINUS::DECLATATION::NUM::MINUS::VAR::SEMICOLON:
:LINE-17::NUM::MINUS::VAR::MINUS::DECLATATION::NUM::MINUS::VAR::SEMICOLON:
:LINE-18::NUM::MINUS::VAR::MINUS::DECLATATION::NUM::MINUS::VAR::SEMICOLON:
:LINE-19::EMPTY:
:LINE-20::COMMENT:
:LINE-21::publicP1_PixelDisplayer::LBRACKET::NUM::MINUS::VAR::MINUS::DECLATATION::imgDim::COMMA::P1_PixelArrayinArr::COMMA::NUM::MINUS::VAR::MINUS::DECLATATION::numEpochs::COMMA::NUM::MINUS::VAR::MINUS::DECLATATION::neighbour::COMMA::NUM::MINUS::VAR::MINUS::DECLATATION::learning::RBRACKET::LBRACE:
:LINE-22::UNKNOWNVAR::EQUAL::inArr::SEMICOLON:
:LINE-23::UNKNOWNVAR::EQUAL::numEpochs::SEMICOLON:
:LINE-24::UNKNOWNVAR::EQUAL::imgDim::SEMICOLON:
:LINE-25::UNKNOWNVAR::EQUAL::neighbour::SEMICOLON:
:LINE-26::UNKNOWNVAR::EQUAL::learning::SEMICOLON:
:LINE-27::this.setPreferredSize::LBRACKET::NEW::Dimension::LBRACKET::imgDim::PLUS::NUM::MINUS::LITERAL::COMMA::imgDim::PLUS::NUM::MINUS::LITERAL::RBRACKET::RBRACKET::SEMICOLON:
:LINE-28::this.setTitle::LBRACKET::STRING::MINUS::OF::MINUS::TEXT::RBRACKET::SEMICOLON:
:LINE-29::EMPTY:
:LINE-30::UNKNOWNVAR::EQUAL::NEW::JButton::LBRACKET::STRING::MINUS::OF::MINUS::TEXT::RBRACKET::SEMICOLON:
:LINE-31::UNKNOWNVAR::EQUAL::NEW::JPanel::LBRACKET::RBRACKET::SEMICOLON:
:LINE-32::panel.add::LBRACKET::UNKNOWNVAR::RBRACKET::SEMICOLON:
:LINE-33::this.getContentPane::LBRACKET::RBRACKET::.add::LBRACKET::UNKNOWNVAR::RBRACKET::SEMICOLON:
:LINE-34::button.addActionListener::LBRACKET::THIS::RBRACKET::SEMICOLON:
:LINE-35::EMPTY:
:LINE-36::this.pack::LBRACKET::RBRACKET::SEMICOLON:
:LINE-37::this.setDefaultCloseOperation::LBRACKET::EXIT_ON_CLOSE::RBRACKET::SEMICOLON:
:LINE-38::RBRACE::COMMENT:
:LINE-39::EMPTY:
:LINE-40::COMMENT:
:LINE-41::AND::Override:
:LINE-42::publicvoidactionPerformed::LBRACKET::ActionEvente::RBRACKET::LBRACE:
:LINE-43::BeginAlgorithm::LBRACKET::RBRACKET::SEMICOLON:
:LINE-44::RBRACE::COMMENT:
:LINE-45::EMPTY:
:LINE-46::EMPTY:
:LINE-47::privatevoidBeginAlgorithm::LBRACKET::RBRACKET::LBRACE:
:LINE-48::EMPTY:
:LINE-49::UNKNOWNVAR::EQUAL::NEW::Random::LBRACKET::RBRACKET::SEMICOLON:
:LINE-50::NUM::MINUS::VAR::MINUS::DECLATATION::NUM::MINUS::VAR::SEMICOLON:
:LINE-51::NUM::MINUS::VAR::MINUS::DECLATATION::NUM::MINUS::VAR::SEMICOLON:
:LINE-52::NUM::MINUS::VAR::MINUS::DECLATATION::NUM::MINUS::VAR::SEMICOLON:
:LINE-53::EMPTY:
:LINE-54::FOR::MINUS::LOOP:
:LINE-55::EMPTY:
:LINE-56::UNKNOWNVAR::EQUAL::LBRACKET::NUM::MINUS::VAR::MINUS::DECLATATION::RBRACKET::LBRACKET::rng.nextFloat::LBRACKET::RBRACKET::STAR::NUM::MINUS::LITERAL::RBRACKET::SEMICOLON:
:LINE-57::UNKNOWNVAR::EQUAL::LBRACKET::NUM::MINUS::VAR::MINUS::DECLATATION::RBRACKET::LBRACKET::rng.nextFloat::LBRACKET::RBRACKET::STAR::NUM::MINUS::LITERAL::RBRACKET::SEMICOLON:
:LINE-58::UNKNOWNVAR::EQUAL::LBRACKET::NUM::MINUS::VAR::MINUS::DECLATATION::RBRACKET::LBRACKET::rng.nextFloat::LBRACKET::RBRACKET::STAR::NUM::MINUS::LITERAL::RBRACKET::SEMICOLON:
:LINE-59::EMPTY:
:LINE-60::pixArr.updateArr::LBRACKET::UNKNOWNVAR::COMMA::UNKNOWNVAR::COMMA::UNKNOWNVAR::COMMA::UNKNOWNVAR::COMMA::UNKNOWNVAR::COMMA::UNKNOWNVAR::COMMA::UNKNOWNVAR::RBRACKET::SEMICOLON:
:LINE-61::EMPTY:
:LINE-62::UNKNOWNVAR::EQUAL::NUM::MINUS::LITERAL::RBRACKET::LBRACE:
:LINE-63::JAVAIO:
:LINE-64::repaint::LBRACKET::RBRACKET::SEMICOLON:
:LINE-65::RBRACE:
:LINE-66::EMPTY:
:LINE-67::RBRACE::COMMENT:
:LINE-68::EMPTY:
:LINE-69::EMPTY:
:LINE-70::RBRACE::COMMENT:
:LINE-71::EMPTY:
:LINE-72::COMMENT:
:LINE-73::AND::Override:
:LINE-74::publicvoidpaint::LBRACKET::Graphicsg::RBRACKET::LBRACE:
:LINE-75::super.paint::LBRACKET::g::RBRACKET::SEMICOLON:
:LINE-76::EMPTY:
:LINE-77::COMMENT:
:LINE-78::NUM::MINUS::VAR::MINUS::DECLARATION::NUM::MINUS::VAR::EQUAL::NUM::MINUS::LITERAL::SEMICOLON:
:LINE-79::NUM::MINUS::VAR::MINUS::DECLARATION::NUM::MINUS::VAR::EQUAL::NUM::MINUS::LITERAL::SEMICOLON:
:LINE-80::EMPTY:
:LINE-81::COMMENT:
:LINE-82::COMMENT:
:LINE-83::EMPTY:
:LINE-84::drawArray::LBRACKET::g::RBRACKET::SEMICOLON:
:LINE-85::RBRACE:
:LINE-86::EMPTY:
:LINE-87::COMMENT:
:LINE-88::privatevoiddrawArray::LBRACKET::Graphicsg::RBRACKET::LBRACE:
:LINE-89::EMPTY:
:LINE-90::UNKNOWNVAR::EQUAL::pixArr.getArrayDimension::LBRACKET::RBRACKET::SEMICOLON:
:LINE-91::UNKNOWNVAR::EQUAL::pixArr.getArray::LBRACKET::RBRACKET::SEMICOLON:
:LINE-92::EMPTY:
:LINE-93::FOR::MINUS::LOOP:
:LINE-94::FOR::MINUS::LOOP:
:LINE-95::FOR::MINUS::LOOP:
:LINE-96::UNKNOWNVAR::EQUAL::NEW::Color::LBRACKET::arr::LSQUAREBRACKET::UNKNOWNVAR::RSQUAREBRACKET::LSQUAREBRACKET::UNKNOWNVAR::RSQUAREBRACKET::LSQUAREBRACKET::NUM::MINUS::LITERAL::RSQUAREBRACKET::COMMA::arr::LSQUAREBRACKET::UNKNOWNVAR::RSQUAREBRACKET::LSQUAREBRACKET::UNKNOWNVAR::RSQUAREBRACKET::LSQUAREBRACKET::NUM::MINUS::LITERAL::RSQUAREBRACKET::COMMA::arr::LSQUAREBRACKET::UNKNOWNVAR::RSQUAREBRACKET::LSQUAREBRACKET::UNKNOWNVAR::RSQUAREBRACKET::LSQUAREBRACKET::NUM::MINUS::LITERAL::RSQUAREBRACKET::RBRACKET::SEMICOLON:
:LINE-97::g.setColor::LBRACKET::UNKNOWNVAR::RBRACKET::SEMICOLON:
:LINE-98::g.drawLine::LBRACKET::NUM::MINUS::LITERAL::PLUS::UNKNOWNVAR::COMMA::NUM::MINUS::LITERAL::PLUS::UNKNOWNVAR::COMMA::NUM::MINUS::LITERAL::PLUS::UNKNOWNVAR::COMMA::NUM::MINUS::LITERAL::PLUS::UNKNOWNVAR::RBRACKET::SEMICOLON:
:LINE-99::RBRACE::COMMENT:
:LINE-100::RBRACE::COMMENT:
:LINE-101::RBRACE::COMMENT:
:LINE-102::RBRACE::COMMENT:
:LINE-103::EMPTY:
:LINE-104::COMMENT:
:LINE-105::publicvoidtoggleShow::LBRACKET::RBRACKET::LBRACE:
:LINE-106::this.setVisible::LBRACKET::NOT::this.isVisible::LBRACKET::RBRACKET::RBRACKET::SEMICOLON:
:LINE-107::RBRACE::COMMENT:
:LINE-108::EMPTY:
:LINE-109::RBRACE::COMMENT:
:EOF: